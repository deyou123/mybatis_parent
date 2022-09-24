package org.mybatis.example;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.example.common.MysqlDBUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.example.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public class ExecutorExample {
    @BeforeEach
    public void initData(){
        MysqlDBUtils.initData();
    }
    @Test
    public void testtestExecutor() throws SQLException, IOException {
        // 获取配置文件输入流
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder的build()方法创建SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 调用openSession()方法创建SqlSession实例
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Configuration configuration = sqlSession.getConfiguration();

        MappedStatement allUsersStmt = configuration.getMappedStatement( "org.mybatis.example.mapper.UserMapper.getAllUsers" );

        //创建ReuseExecutor实例
        Executor reuseExecutor = configuration.newExecutor(
                new JdbcTransaction(sqlSession.getConnection()),
                ExecutorType.REUSE
        );
        // 调用query()方法执行查询操作
        List<User> userList =  reuseExecutor.query(allUsersStmt,
                null,
                //第一个参数结果偏移量，第二个参数，
                new RowBounds(2,5),
                Executor.NO_RESULT_HANDLER);
       userList.forEach( System.out::println );
    }
}