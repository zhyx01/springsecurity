package com.ax.config;

import com.ax.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * className: SecurityPasswordEncodeConfig
 * description: 处理密码存储
 *
 * @author: axiang
 * date: 2023/4/6 10:34
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 创建BCryptPasswordEncoder并注入容器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 使用 AuthenticationManager 进行用户认证
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 设置放行
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //关闭csrf
                .csrf().disable()
                // 不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许登录接口进行匿名访问
                .antMatchers("/user/login").anonymous()
                // 除了上面的接口之外, 所有的请求都需要鉴权认证
                .anyRequest().authenticated();

        // 把token校验过滤器添加到过滤器链中
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}