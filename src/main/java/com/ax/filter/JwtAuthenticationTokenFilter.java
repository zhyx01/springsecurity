package com.ax.filter;

import com.ax.domain.LoginUser;
import com.ax.utils.JwtUtil;
import com.ax.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * className: JwtAuthenticationTokenFilter
 * description: 登录认证过滤器
 *
 * @author: axiang
 * date: 2023/4/6 16:19
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 获取token
        String token = request.getHeader("token");
        // 判断是否为空
        if (Objects.isNull(token)) {
            //放行 , 进入到下一个过滤器(后面的过滤器还会有相应的判断, 并拦截)
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // 从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login" + userId);
        // 判断是否为空
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }

        // 存入SecurityContextHolder

        // TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
