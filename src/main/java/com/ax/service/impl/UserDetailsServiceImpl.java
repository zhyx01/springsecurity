package com.ax.service.impl;

import com.ax.domain.LoginUser;
import com.ax.domain.User;
import com.ax.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * className: UserDetailServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/4/6 10:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> queryMapper = new LambdaQueryWrapper<>();

        queryMapper.eq(User::getUserName, userName);

        // 根据用户名查询用户信息
        User user = userMapper.selectOne(queryMapper);

        // 判断是否查到用户信息
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }

        // todo 根据用户查询权限信息

        // 封装成UserDetails对象返回
        return new LoginUser(user);
    }
}
