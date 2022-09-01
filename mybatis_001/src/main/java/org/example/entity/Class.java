package org.example.entity;

import lombok.Data;

import java.util.List;

/**
 * @author DeYou
 * @date 2022/8/31
 */
@Data
public class Class {
    private int id;
    private String name;
    private List<Student> students;
}