package org.example.mapper;

import org.example.entity.User;

import java.util.List;

/**
 * @author DeYou
 * @date 2022/8/30
 */
public interface UserMapper {

    List<User> getUsers();
    boolean deleteUser(int id   );

    User getUserById(int id);

    boolean insertUser(User user);

    boolean updateUser(User user);
}