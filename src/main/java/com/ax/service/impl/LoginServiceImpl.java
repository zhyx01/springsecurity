package com.ax.service.impl;

import com.ax.controller.commen.Result;
import com.ax.domain.LoginUser;
import com.ax.domain.User;
import com.ax.service.LoginService;
import com.ax.utils.JwtUtil;
import com.ax.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * className: LoginServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/4/6 14:25
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {

        // AuthenticationManager authenticate 进行用户认证

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 如果认证没通过, 给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 认证通过了, 使用userid生成一个jwt jwt存入Result返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        // 将完整的登录用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 把token相应给前端
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new Result(200, "登录成功", map);
    }
}
