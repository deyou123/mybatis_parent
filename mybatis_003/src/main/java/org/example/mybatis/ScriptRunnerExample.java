package org.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.example.common.DbUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class ScriptRunnerExample {
    @Test
    public void testScript() throws Exception {
        Connection connection = DriverManager.getConnection( "jdbc:hsqldb:mem:mybatis", "ddid", "" );
        ScriptRunner scriptRunner = new ScriptRunner( connection );
        scriptRunner.runScript( Resources.getResourceAsReader( "create-table.sql" ) );
        scriptRunner.runScript( Resources.getResourceAsReader( "db/hsql/init-data.sql" ) );

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( "select * from user" );
        DbUtils.dumpRS( resultSet );
    }
}