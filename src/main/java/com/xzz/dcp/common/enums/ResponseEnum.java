package com.xzz.dcp.common.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    /**
     * 用户名被占用
     */
    USERNAME_IS_TAKEN(10, "用户名被占用"),

    /**
     * 未知的异常
     */
    UNDEFINED(11, "未知的异常"),

    /**
     * 购物车已满，不能添加新 sku
     */
    SHOP_CART_SKU_TYPE_COUNT_FULL(21, "购物车已满，不能添加新sku"),

    /**
     * 数据库不存在此 sku
     */
    SKU_IS_NOT_EXISTS(22, "数据库不存在此sku"),

    /**
     * sku 已下架
     */
    SKU_IS_OFF_THE_SHELVES(23, "sku已下架"),

    ;

    private final Integer code;

    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
