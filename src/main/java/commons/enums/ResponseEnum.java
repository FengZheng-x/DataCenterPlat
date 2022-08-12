package commons.enums;

import lombok.Getter;

public enum ResponseEnum {
    /**
     * 购物车已满，不能添加新商品
     */
    SHOP_CART_SKU_COUNT_FULL(10000, "购物车已满，不能添加新商品"),

    /**
     * 数据库不存在此sku
     */
    SKU_NOT_EXISTS(10100, "数据库不存在此sku"),

    /**
     * sku 已下架
     */
    SKU_IS_OFF_THE_SHELVES(10101, "该sku已下架");

    @Getter
    private final Integer code;

    @Getter
    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
