package com.ax;

import com.ax.config.SecurityPasswordEncodeConfig;
import com.ax.domain.User;
import com.ax.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * className: MapperTest
 * description:
 *
 * @author: axiang
 * date: 2023/4/6 9:58
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SecurityPasswordEncodeConfig securityPasswordEncodeConfig;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testPasswordEncode() {
        String encode = securityPasswordEncodeConfig.passwordEncoder().encode("000000");
        System.out.println(encode);

        System.out.println(securityPasswordEncodeConfig.passwordEncoder()
                        .matches("000000", "$2a$10$xsJ3o3PVcoz02Ub3ZApMd.47XLu9XBh73z9h9.FvIaYZcTWyo976C"));
    }
}
