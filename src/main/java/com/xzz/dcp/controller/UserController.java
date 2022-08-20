package com.xzz.dcp.controller;

import com.xzz.dcp.entity.User;
import com.xzz.dcp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("register")
    public ResponseEntity<Void> register(User user) {
        userService.register(user);
        return ResponseEntity.ok().build();
    }
}
