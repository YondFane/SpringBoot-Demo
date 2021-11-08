package com.yfan.springbootmybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yfan.springbootmybatisplus.entity.User;
import com.yfan.springbootmybatisplus.mapper.UserMapper;
import com.yfan.springbootmybatisplus.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
