package org.example.jdbc;

import org.example.common.DbUtils;
import org.example.common.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class Example01 {


    @Test
    @DisplayName("Class.forName获取驱动")
    public void testJDBC(){
        DbUtils.initData();

        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1. 加载驱动
            Class.forName( "org.hsqldb.jdbcDriver" );
            //获取连接
            connection = DriverManager.getConnection( "jdbc:hsqldb:mem:mybatis", "sa", "" );

            statement = connection.createStatement();
            resultSet = statement.executeQuery( "select * from user" );

            ResultSetMetaData metaData = resultSet.getMetaData();
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

        } catch (ClassNotFoundException e) {
            throw new RuntimeException( e );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }finally {
            //关闭连接对象
            IOUtils.closeQuietly( resultSet );
            IOUtils.closeQuietly(statement);
            IOUtils.closeQuietly(connection);
        }

    }
}