package org.example.entity;

import lombok.Data;

import java.util.List;

/**
 * @author DeYou
 * @date 2022/9/3
 */
@Data
public class Course {
    private int id;
    private String name;
    private List<Student> students;
}