package org.mybatis.example.xpath;

import lombok.Data;

import java.util.Date;

/**
 * @author DeYou
 * @date 2022/9/23
 */
@Data
public class User {

    private Long id;
    private String name;
    private Date createTime;
    private String password;
    private String phone;
    private String nickName;
}