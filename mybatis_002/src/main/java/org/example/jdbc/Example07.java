package org.example.jdbc;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
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
public class Example07 {
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

            //使用预编译

            statement = connection.createStatement();
            String sql = "insert into user(create_time, name, password, phone, nick_name) " +
                    "values('2022-09-21 10:20:30','User1','test','18700001111','User1');";
            //返回主键
            statement.executeUpdate( sql,Statement.RETURN_GENERATED_KEYS );
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                System.out.println("自增长主键：" + generatedKeys.getInt(1));
            }



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


}