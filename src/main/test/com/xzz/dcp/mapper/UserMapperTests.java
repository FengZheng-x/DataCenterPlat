package com.xzz.dcp.mapper;

import com.xzz.dcp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// 表示当前类是测试类，不会随项目一同打包
@SpringBootTest
// 表示启动这个单元测试
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("Alice").setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }
}
