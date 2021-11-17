package com.yfan.springbootredis.service;

import com.yfan.springbootredis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test(){
        User user = userService.select(1);
        System.out.println(user.toString());
        // 控制台只输出一遍查询日志 select user= User(id=1, name=JACK, age=20)
        user = userService.select(1);
        System.out.println(user.toString());
    }

    @Test
    public void testDelete(){
        // 清空缓存kEY 1
        userService.delete(1);
    }
}
