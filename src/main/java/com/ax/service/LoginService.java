package com.ax.service;

import com.ax.controller.commen.Result;
import com.ax.domain.User;

public interface LoginService {
    Result login(User user);

}
