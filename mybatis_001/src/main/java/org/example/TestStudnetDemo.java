package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.entity.Class;
import org.example.entity.Student;
import org.example.mapper.ClassMapper;
import org.example.mapper.StudentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author DeYou
 * @date 2022/8/31
 */
public class TestStudnetDemo {

    @Test
    @DisplayName("测试查询学生班级,一对一查询")
    public void findById(){
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession();
        StudentMapper studentMapper = sqlSession.getMapper( StudentMapper.class );
        Student student = studentMapper.findById( 1 );
        System.out.println(student);
    }


    @Test
    @DisplayName("测试班级查询学生，一对多查询")
    public void findByIdTest() throws Exception {}{
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream( "mybatis-config.xml" );
        try {
            resourceAsStream = Resources.getResourceAsStream( "mybatis-config.xml" );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build( resourceAsStream );
        SqlSession sqlSession = build.openSession();
        ClassMapper classMapper = sqlSession.getMapper( ClassMapper.class );
        Class aClass = classMapper.findById( 1 );
        System.out.println(aClass);
    }
}