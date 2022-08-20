package com.xzz.dcp.mapper;

import com.xzz.dcp.entity.User;

public interface UserMapper {
    /**
     * 插入用户的数据
     *
     * @param user 用户的数据
     * @return 受影响的行数(增删改都将受影响的行数作为返回值, 可以根据返回值来判断是否执行成功)
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据, 如果没有找到则返回null
     */
    User findByUsername(String username);
}
