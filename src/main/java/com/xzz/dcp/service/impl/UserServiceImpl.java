package com.xzz.dcp.service.impl;

import com.xzz.dcp.common.enums.ResponseEnum;
import com.xzz.dcp.entity.User;
import com.xzz.dcp.mapper.UserMapper;
import com.xzz.dcp.service.IUserService;
import com.xzz.dcp.service.ex.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

// 将当前类的对象交给 SpringBoot 管理，自动创建对象以及其维护
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        // 判断用户名是否已被注册
        User result = userMapper.findByUsername(user.getUsername());
        if (result != null) {
            log.error(ResponseEnum.USERNAME_IS_TAKEN.getMessage());
            throw new ValidateException(ResponseEnum.USERNAME_IS_TAKEN);
        }
        Date date = new Date();
        user.setIsDelete(0)
                .setCreateTime(date)
                .setUpdateTime(date);
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        user.setPassword(encrypt(user.getPassword(), salt));

        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            log.error("注册时{}", ResponseEnum.UNDEFINED.getMessage());
            throw new ValidateException(ResponseEnum.UNDEFINED);
        }
        log.info("用户 {} 注册成功", user.getUsername());
    }

    private String encrypt(String password, String salt) {
        // MD5 加密算法
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
