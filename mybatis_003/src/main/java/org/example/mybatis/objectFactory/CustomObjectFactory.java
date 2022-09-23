package org.example.mybatis.objectFactory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;


import java.util.Objects;
import java.util.UUID;

/**
 * @author DeYou
 * @date 2022/9/21
 */
public class CustomObjectFactory extends DefaultObjectFactory {
    @Override
    public Object create(Class type) {
        if(type.equals( User.class)){
            //实例化User类
            User user = (User)super.create(type);


            user.setUuid( UUID.randomUUID().toString());
            return user;
        }
        return super.create( type );
    }
}