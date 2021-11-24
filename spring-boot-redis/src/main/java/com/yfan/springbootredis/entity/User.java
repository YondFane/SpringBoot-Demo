package com.yfan.springbootredis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * User
 * @Author YFAN
 * @Date 2021/11/24 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 2852248514183451461L;
    private Integer id;
    private String name;
    private Integer age;
}
