package org.example.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author DeYou
 * @date 2022/9/1
 */
public class MysqlDBUtils extends DbUtils {
    private static String user;
    private static String password;
    private static String jdbcUrl;
    private static String driverClass;

   static  {
        Properties properties = new Properties();
        try {
            properties.load( Resources.getResourceAsStream( "jdbc.properties" ) );
            user = properties.getProperty( "jdbc.user" );
            password = properties.getProperty( "jdbc.password" );
            jdbcUrl = properties.getProperty( "jdbc.jdbcUrl" );
            driverClass = properties.getProperty( "jdbc.driverClass" );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

    }

    /**
     * 初始化mysql 数据并获取sql 连接
     * @return
     */
    public static Connection initData() {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            // 获取Connection对象
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            // 使用Mybatis的ScriptRunner工具类执行数据库脚本
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            // 不输出sql日志
            scriptRunner.setLogWriter(null);
            //创建数据库表
            scriptRunner.runScript( Resources.getResourceAsReader( "db/mybatis.sql" ));
            //初始化数据
            //scriptRunner.runScript(Resources.getResourceAsReader("init-data.sql"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }



   /* public static void main(String[] args) {
        initData();
    }*/
}