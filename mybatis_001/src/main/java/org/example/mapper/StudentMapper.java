package org.example.mapper;

import org.example.entity.Student;

/**
 * @Author deyou
 * @Date 2022/8/31 16:41
 * @Version 1.0
 */
public interface StudentMapper {
    Student findById(long id);
}
