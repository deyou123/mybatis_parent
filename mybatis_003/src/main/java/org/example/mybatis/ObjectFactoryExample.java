package org.example.mybatis;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.example.mybatis.objectFactory.CustomObjectFactory;
import org.example.mybatis.objectFactory.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author DeYou
 * @date 2022/9/21
 */
public class ObjectFactoryExample {
    @Test
    public void testObjectFactory() throws Exception {
        DefaultObjectFactory objectFactory = new DefaultObjectFactory();
        //使用对象工厂创建一个list 数组
        List<Integer> list = objectFactory.create( List.class );
        Map<String,Object> map = objectFactory.create( Map.class );
        list.addAll( Arrays.asList(1,2,3) );

        map.put( "V1","k1" );
        System.out.println(list);
        System.out.println(map);
        //创建的User 对象自动生成UUID
        CustomObjectFactory customObjectFactory = new CustomObjectFactory();
        User user = (User)customObjectFactory.create( User.class );
        user.setName( "tom" );
        System.out.println( user );

    }


}