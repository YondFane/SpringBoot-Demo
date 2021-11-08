package com.yfan.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yfan.springbootmybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/*
 * 继承BaseMapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
