package com.xzz.dcp.common.enums;

import lombok.Getter;

/**
 * 购物车相应枚举
 */
@Getter
public enum ShopCartResponseEnum {

    /**
     * 购物车已满，不能添加新 sku
     */
    SHOP_CART_SKU_TYPE_COUNT_FULL(10000, "购物车已满，不能添加新sku"),

    /**
     * 数据库不存在此 sku
     */
    SKU_IS_NOT_EXISTS(10100, "数据库不存在此sku"),

    /**
     * sku 已下架
     */
    SKU_IS_OFF_THE_SHELVES(10101, "sku已下架"),

    ;

    private final Integer code;

    private final String message;

    ShopCartResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
