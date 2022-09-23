package org.example.jdbc;

import org.example.common.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class Example08 {
    @Test
    @DisplayName( "获取数据库的基本信息")
    public void testDbMetaData() {
        try {
            //获取hsqldb 数据库信息
            //Class.forName("org.hsqldb.jdbcDriver");
            //Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis","sa", "");

            //获取mysql 数据库的信息
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC","root","666666" );

            DatabaseMetaData databaseMetaData = conn.getMetaData();
            System.out.println("数据库URL:" + databaseMetaData.getURL());
            System.out.println("数据库用户名:" + databaseMetaData.getUserName());
            System.out.println("数据库产品名:" + databaseMetaData.getDatabaseProductName());
            System.out.println("数据库产品版本:" + databaseMetaData.getDatabaseProductVersion());
            System.out.println("驱动主版本:" + databaseMetaData.getDriverMajorVersion());
            System.out.println("驱动副版本:" + databaseMetaData.getDriverMinorVersion());
            System.out.println("数据库供应商用于schema的首选术语:" + databaseMetaData.getSchemaTerm());
            System.out.println("数据库供应商用于catalog的首选术语:" + databaseMetaData.getCatalogTerm());
            System.out.println("数据库供应商用于procedure的首选术语:" + databaseMetaData.getProcedureTerm());
            System.out.println("null值是否高排序:" + databaseMetaData.nullsAreSortedHigh());
            System.out.println("null值是否低排序:" + databaseMetaData.nullsAreSortedLow());
            System.out.println("数据库是否将表存储在本地文件中:" + databaseMetaData.usesLocalFiles());
            System.out.println("数据库是否为每个表使用一个文件:" + databaseMetaData.usesLocalFilePerTable());
            System.out.println("数据库SQL关键字:" + databaseMetaData.getSQLKeywords());
            IOUtils.closeQuietly(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}