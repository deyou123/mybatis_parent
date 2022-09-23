package org.example.mybatis;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.jdbc.SqlRunner;
import org.example.common.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;



/**
 * @author DeYou
 * @date 2022/9/21
 */
public class SqlRunnerExample {

    Connection connection = null;

    @BeforeEach
    public void initTable() throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis",
                "sa", "");
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setLogWriter(null);
        scriptRunner.runScript( Resources.getResourceAsReader("create-table.sql"));
        scriptRunner.runScript(Resources.getResourceAsReader("init-data.sql"));
    }
    @Test
    @DisplayName( "测试查询数据")
    public void SelectOne() throws SQLException {
        SqlRunner sqlRunner = new SqlRunner( connection );
        SQL sql = new SQL() {{SELECT("*");
                FROM("user");
                WHERE("id = ?");
        }};
        Map<String, Object> resultMap = sqlRunner.selectOne( sql.toString(), 1 );
        System.out.println( JSON.toJSONString( resultMap ) );

    }

    @Test
    @DisplayName( "测试删除数据")
    public void testDelete() throws SQLException {
        SqlRunner sqlRunner = new SqlRunner(connection);
        String deleteUserSql = new SQL(){{
            DELETE_FROM("user");
            WHERE("id = ?");
        }}.toString();
        sqlRunner.delete(deleteUserSql, Integer.valueOf(1));
    }

    @Test
    public void testUpdate() throws SQLException {
        SqlRunner sqlRunner = new SqlRunner(connection);
        String updateUserSql = new SQL(){{
            UPDATE("user");
            SET("nick_name = ?");
            WHERE("id = ?");
        }}.toString();
        sqlRunner.update(updateUserSql, "Jane", Integer.valueOf(1));
    }

    @Test
    public void testInsert() throws SQLException {
        SqlRunner sqlRunner = new SqlRunner(connection);
        String insertUserSql = new SQL(){{
            INSERT_INTO("user");
            INTO_COLUMNS("create_time,name,password,phone,nick_name");
            INTO_VALUES("?,?,?,?,?");
        }}.toString();
        String createTime = LocalDateTime.now().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        sqlRunner.insert(insertUserSql,createTime,"Jane","test","18700000000","J");
    }

    @AfterEach
    public void closeConnection() {
        IOUtils.closeQuietly(connection);
    }



}