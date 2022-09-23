package org.example.jdbc;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.common.IOUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class Example05 {
    @BeforeEach
    @DisplayName( "每次测试初始化数据库" )
    public void initData() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","666666" );

        ScriptRunner scriptRunner = new ScriptRunner( connection );
        //设置日志 ,关闭日志，默认打印日志在控制台
        scriptRunner.setLogWriter( null );
        //执行脚本,这里使用了mysql 数据库脚本
        scriptRunner.runScript( Resources.getResourceAsReader( "db/create-user.sql" ) );
    }

    @Test
    @DisplayName( "使用外部配置文件" )
    public void testJDBC(){


        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory();
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream( "database.properties" ));
            unpooledDataSourceFactory.setProperties( properties );
            DataSource dataSource = unpooledDataSourceFactory.getDataSource();
            //获取连接
            connection = dataSource.getConnection( );
            statement = connection.createStatement();

            statement.addBatch( "INSERT INTO " +
                    "`mybatis`.`user` (`name`, `price`) " +
                    "VALUES ('lisi', 2000)" );
            statement.addBatch( "INSERT INTO " +
                    "`mybatis`.`user` (`name`, `price`) " +
                    "VALUES ('tom', 2000)" );
            statement.executeBatch();

            resultSet = statement.executeQuery( "select * from user" );
            dumpRs( resultSet );

        }  catch (SQLException e) {
            throw new RuntimeException( e );
        } catch (IOException e) {
            throw new RuntimeException( e );
        } finally {
            //关闭连接对象
            IOUtils.closeQuietly( resultSet );
            IOUtils.closeQuietly(statement);
            IOUtils.closeQuietly(connection);
        }

    }

    /**
     * 封装结果输出方法
     * @param resultSet
     * @throws SQLException
     */
    public void dumpRs(ResultSet resultSet) throws SQLException {
        //检索此ResultSet对象的列的数量、类型和属性。
        ResultSetMetaData metaData = resultSet.getMetaData();
        //返回此ResultSet对象中的列数
        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName( i );
                String columnVal = resultSet.getString( columnName);
                System.out.println(columnName + ": " + columnVal);
            }
            System.out.println("--------------------------------------");
        }

    }
}