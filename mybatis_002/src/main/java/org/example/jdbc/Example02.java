package org.example.jdbc;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.example.common.DbUtils;
import org.example.common.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class Example02 {


    @Test
    @DisplayName( "使用数据库连接池" )
    public void testJDBC(){
        DbUtils.initData();

        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1. 加载驱动
            // 创建DataSource实例
            DataSource dataSource = new UnpooledDataSource("org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:mem:mybatis", "sa", "");
            //获取连接
            connection = dataSource.getConnection( );
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

        }  catch (SQLException e) {
            throw new RuntimeException( e );
        }finally {
            //关闭连接对象
            IOUtils.closeQuietly( resultSet );
            IOUtils.closeQuietly(statement);
            IOUtils.closeQuietly(connection);
        }

    }
}