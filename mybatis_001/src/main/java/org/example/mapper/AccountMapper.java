package org.example.mapper;
import org.example.entity.Class;

/**
 * @author DeYou
 * @date 2022/8/31
 */
public interface AccountMapper {
     Class findById(Integer id);
}