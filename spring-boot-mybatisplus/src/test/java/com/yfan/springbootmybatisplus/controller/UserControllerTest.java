package com.yfan.springbootmybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.yfan.springbootmybatisplus.entity.User;
import com.yfan.springbootmybatisplus.mapper.UserMapper;
import com.yfan.springbootmybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /*
     * 查询
     * @author YFAN
     * @date 2021/11/8/008
     * @param  * @param
     * @return void
     */
    @Test
    public void select() {
        System.out.println("---selectList---");
        List<User> list = userMapper.selectList(null);
        list.forEach(o->{
            System.out.println(o);
        });
    }

    /*
     * 修改
     * @author YFAN
     * @date 2021/11/8/008
     * @param  * @param
     * @return void
     */
    @Test
    public void update() {
        User user = userMapper.selectById("1");
        user.setAge(120);
        userMapper.updateById(user);
    }

    /*
     * 结合service类进行使用
     * @author YFAN
     * @date 2021/11/8/008
     * @param  * @param
     * @return void
     */
    @Test
    public void UserServiceTest(){
        Map<String, Object> map = userService.getMap(null);
        System.out.println(map.size());
    }

}
