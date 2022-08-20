package com.xzz.dcp.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
public class User extends TimeEntity {
    /**
     * 用户 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐
     * 在散列之前将密码的任意固定位置插入特定的字符串，
     * 这个在散列中加入字符串的方式称为「加盐」
     */
    private String salt;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电子邮箱地址
     */
    private String email;

    /**
     * 性别
     * 1 表示男
     * 0 表示女
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 该用户是否被删除
     * 0 已删除
     * 1 未被删除
     */
    private Integer isDeleted;
}
