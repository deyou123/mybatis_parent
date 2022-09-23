package org.example.jdbc;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.example.common.DbUtils;
import org.example.common.IOUtils;
import org.example.common.MysqlDBUtils;
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
public class Example03 {


    @Test
    @DisplayName( "使用外部配置文件" )
    public void testJDBC(){
        MysqlDBUtils.initData();

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