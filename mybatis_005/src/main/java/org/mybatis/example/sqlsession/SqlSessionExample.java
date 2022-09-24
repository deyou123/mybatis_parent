package org.mybatis.example.sqlsession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public class SqlSessionExample {
    @Test
    public void testSqlSession() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = sqlSessionFactory.openSession();
    }
}