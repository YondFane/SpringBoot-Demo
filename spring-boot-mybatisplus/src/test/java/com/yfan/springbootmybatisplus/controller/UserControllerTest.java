package com.yfan.springbootmybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        System.out.println("获取一条记录");
        Map<String, Object> map = userService.getMap(null);
        System.out.println(map.size());
        System.out.println("根据条件获取一条记录");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age","20");
        User one = userService.getOne(queryWrapper);
        System.out.println(one);
        System.out.println("获取所有记录");
        List<User> list = userService.list();
        list.forEach(user->{
            System.out.println(user);
        });
        System.out.println("分页查询1");
        // 页面查询
        Page<User> page = new Page<>(1,2);
//        page.setSize(2);//每页大小
//        page.setCurrent(1);//当前页
        List<User> records = userService.page(page).getRecords();
        records.forEach(user -> {
            System.out.println(user);
        });
        System.out.println("分页查询2");
        List<User> records1 = userMapper.selectPage(page, null).getRecords();
        records1.forEach(user -> {
            System.out.println(user);
        });
    }

}
