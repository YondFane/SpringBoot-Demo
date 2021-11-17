package com.yfan.springbootredis.service.impl;

import com.yfan.springbootredis.entity.User;
import com.yfan.springbootredis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/*
 * UserService
 * @author YFAN
 * @date 2021/11/17/017
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /*
     * 模拟数据库数据
     */
    private static HashMap<Integer, User> DATAMAP = new HashMap<>();
    static {
        DATAMAP.put(1, new User(1,"JACK",20));
        DATAMAP.put(2, new User(2,"TOM",22));
        DATAMAP.put(3, new User(3,"MARK",23));
    }

    /*
     * 查询用户
     * @author YFAN
     * @date 2021/11/17/017
     * @param  * @param id
     * @return com.yfan.springbootredis.entity.User
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User select(Integer id) {
        log.info("select user= {}", DATAMAP.get(id));
        return DATAMAP.get(id);
    }

    /*
     * 修改用户
     * @author YFAN
     * @date 2021/11/17/017
     * @param  * @param user
     * @return void
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public void update(User user) {
        log.info("update user= {}", user);
        DATAMAP.put(user.getId(), user);
    }

    /*
     * 删除用户
     * @author YFAN
     * @date 2021/11/17/017
     * @param  * @param user
     * @return void
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Integer id) {
        log.info("delete user= {}", DATAMAP.get(id));
        DATAMAP.remove(id);
    }

    /*
     * 查询所有用户
     * @author YFAN
     * @date 2021/11/17/017
     * @param  * @param
     * @return java.util.HashMap<java.lang.Integer,com.yfan.springbootredis.entity.User>
     */
    @Override
    public HashMap<Integer, User> selectALl() {
        return DATAMAP;
    }
}
