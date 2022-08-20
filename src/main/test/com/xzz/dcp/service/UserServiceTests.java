package com.xzz.dcp.service;

import com.xzz.dcp.entity.User;
import com.xzz.dcp.service.ex.ValidateException;
import com.xzz.dcp.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;

// 表示当前类是测试类，不会随项目一同打包
@SpringBootTest
// 表示启动这个单元测试
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void register() {
        User user1 = new User();
        user1.setUsername("Alice").setPassword("123456");
        userService.register(user1);
        User user2 = new User();
        user2.setUsername("Bob").setPassword("qwerty");
        userService.register(user2);
        User user3 = new User();
        user3.setUsername("Carl").setPassword("abc123");
        userService.register(user3);
        assertThrows(ValidateException.class, () -> userService.register(user1));
    }
}
