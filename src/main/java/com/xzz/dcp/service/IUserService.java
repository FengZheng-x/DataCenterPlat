package com.xzz.dcp.service;

import com.xzz.dcp.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {
    /**
     * 用户注册方法
     *
     * @param user 用户的数据对象 {@link User}
     */
    void register(User user);
}
