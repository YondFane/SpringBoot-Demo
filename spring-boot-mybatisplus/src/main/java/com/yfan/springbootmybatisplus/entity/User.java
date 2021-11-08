package com.yfan.springbootmybatisplus.entity;

import lombok.Data;
import lombok.ToString;

//lombok的注解
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
