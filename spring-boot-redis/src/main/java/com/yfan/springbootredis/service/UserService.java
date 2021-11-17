package com.yfan.springbootredis.service;

import com.yfan.springbootredis.entity.User;

import java.util.HashMap;

public interface UserService {

    public User select(Integer id);

    public void update(User user);

    public void delete(Integer id);

    public HashMap<Integer, User> selectALl();
}
