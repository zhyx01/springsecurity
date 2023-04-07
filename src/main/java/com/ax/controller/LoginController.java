package com.ax.controller;

import com.ax.controller.commen.Result;
import com.ax.domain.User;
import com.ax.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * className: LoginController
 * description: 登录认证
 *
 * @author: axiang
 * date: 2023/4/6 14:23
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public Result login(@RequestBody User user) {

        return loginService.login(user);
    }
}
