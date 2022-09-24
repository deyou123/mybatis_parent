package org.mybatis.example;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.example.common.MysqlDBUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.example.entity.User;
import org.mybatis.example.mapper.UserMapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public class MybatisExample {
    @BeforeEach
    public void init(){
        MysqlDBUtils.initData();
    }
    @Test
    public void testGetAllUser() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( Resources.getResourceAsStream( "mybatis-config.xml" ) );
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper( UserMapper.class );
        List userList = userMapper.getAllUsers();
        userList.forEach( System.out::println );

    }

    @Test
    public void testSessionManager() throws IOException {
        Reader mybatisConfig = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(mybatisConfig);
        sqlSessionManager.startManagedSession();
        UserMapper userMapper = sqlSessionManager.getMapper(UserMapper.class);
        List<User> userList = userMapper.getAllUsers();
        System.out.println( JSON.toJSONString(userList));
    }
    @Test
    public void testGetUserById() throws IOException {
        Reader mybatisConfig = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(mybatisConfig);
        sqlSessionManager.startManagedSession();
        UserMapper userMapper = sqlSessionManager.getMapper(UserMapper.class);
        User user = userMapper.getUserById("1");
        System.out.println(user);
    }

    @Test
    public void testGetUserByPhone() throws IOException {
        Reader mybatisConfig = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(mybatisConfig);
        sqlSessionManager.startManagedSession();
        UserMapper userMapper = sqlSessionManager.getMapper(UserMapper.class);
        List<User> users = userMapper.getUserByPhone("18700001111");
        System.out.println(users);
    }
}