package org.example.mysql;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.common.MysqlDBUtils;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.util.List;

/**
 * @author DeYou
 * @date 2022/8/30
 */
public class MysqlConnectDemo {
    @BeforeEach
    public void setUp() throws Exception {
        MysqlDBUtils.initData();
    }

    @Test
    @DisplayName( "插入用户" )
    public void testInsertUser() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession(true);
        Configuration configuration = sqlSession.getConfiguration();
        UserMapper mapper = sqlSession.getMapper( UserMapper.class );
        User user = new User();
        //user.setId( 2);
        user.setName( "张三" );
        user.setPrice( 10000 );
        boolean b = mapper.insertUser(user);
        //sqlSession.commit();
        System.out.println("是否添加用户成功"+b);


    }

    @Test
    @DisplayName( "测试查询所有表" )
    public void test() {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper( UserMapper.class );
        List<User> users = mapper.getUsers();
        users.forEach( System.out::println );
    }

    @Test
    @DisplayName( "根据ID查询用户" )
    public void testGetUserById() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper( UserMapper.class );
        User user = mapper.getUserById( 1 );
        System.out.println( user );

    }

    @Test
    @DisplayName( "测试修改" )
    public void testUpdateUser() throws Exception {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper( UserMapper.class );
        User updateUser = new User( 2, "tom", 3000 );
        boolean b = mapper.updateUser( updateUser );
        sqlSession.commit();
        System.out.println( b );

    }
}