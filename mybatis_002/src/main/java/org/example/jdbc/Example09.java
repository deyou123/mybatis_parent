package org.example.jdbc;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.common.DbUtils;
import org.example.common.IOUtils;
import org.junit.jupiter.api.AfterEach;
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
public class Example09 {
    @BeforeEach
    @DisplayName( "每次测试初始化数据库" )
    public void initData() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","666666" );

        ScriptRunner scriptRunner = new ScriptRunner( connection );
        //设置日志 ,关闭日志，默认打印日志在控制台
        scriptRunner.setLogWriter( null );
        //执行脚本,这里使用了mysql 数据库脚本
        scriptRunner.runScript( Resources.getResourceAsReader( "create-table06.sql" ) );
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

            String sql1 = "insert into user(create_time, name, password, phone, nick_name) " +
                    "values('2010-10-24 10:20:30','User1','test','18700001111','User1')";
            String sql2 = "insert into user(create_time, name, password, phone, nick_name) " +
                    "values('2010-10-24 10:20:30','User2','test','18700001111','User2')";
            //关闭自动提交
            connection.setAutoCommit( false );
            statement = connection.createStatement();
            statement.executeUpdate( sql1 );

            Savepoint savepoint = connection.setSavepoint( "SP1" );
            statement.executeUpdate(sql2);
            // 回滚到保存点
            connection.rollback(savepoint);
            connection.commit();

            ResultSet rs  = connection.createStatement().executeQuery("select * from user ");
            DbUtils.dumpRS(rs);

        }  catch (SQLException e) {
            throw new RuntimeException( e );
        } catch (IOException e) {
            throw new RuntimeException( e );
        } catch (Exception e) {
            throw new RuntimeException( e );
        } finally {
            //关闭连接对象
            IOUtils.closeQuietly( resultSet );
            IOUtils.closeQuietly(statement);
            IOUtils.closeQuietly(connection);
        }

    }


}