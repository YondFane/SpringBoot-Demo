package com.yfan.springbootredis.service;

import com.yfan.springbootredis.entity.User;

import java.util.HashMap;

/**
 * @author YFAN
 */
public interface UserService {
    /**
     * select
     * @Author YFAN
     * @Date 2021/11/24 10:14
     * @params [id] id
     * @return com.yfan.springbootredis.entity.User
     */
    User select(Integer id);
    /**
     * update
     * @Author YFAN
     * @Date 2021/11/24 10:17
     * @params [user] user
     * @return void
     */
    void update(User user);
    /**
     * delete by id
     * @Author YFAN
     * @Date 2021/11/24 10:17
     * @params [id] id
     * @return void
     */
    void delete(Integer id);
    /**
     * selectAll
     * @Author YFAN
     * @Date 2021/11/24 10:17
     * @params []
     * @return java.util.HashMap<java.lang.Integer,com.yfan.springbootredis.entity.User>
     */
    HashMap<Integer, User> selectAll();
}
