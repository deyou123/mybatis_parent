package org.mybatis.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.example.entity.User;

import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/23
 */
public interface UserMapper {

    /**
     * 通过动态sql
     * @param entity
     * @return
     */
    List<User> getUserByEntity(User entity);

    List<User> getAllUsers();
    List<User> getUserByPhone(@Param("phone") String phone);
    @Select("select * from user where id=#{id,jdbcType=INTEGER}")
    User getUserById(@Param("id") String userId);


}