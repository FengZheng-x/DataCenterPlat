package com.xzz.dcp.common.constants;

/**
 * 购物车常量
 */
public interface ShopCartConstant {

    /**
     * 购物车key前缀
     */
    String SHOP_CART = "SHOP_CART:%s";

    /**
     * 用于分组的购物车（后续需要按店铺分组）
     */
    String SHOP_CART_FOR_GROUPING = "SHOP_CART_FOR_GROUPING:%s";

    /**
     * 排序购物车
     */
    String SORT_SHOP_CART = "SORT_SHOP_CART";

    /**
     * 购物车分页所包含的SkuIds（根据排序购物车计算得出）
     */
    String SHOP_CART_PAGE = "SHOP_CART_PAGE";

    /**
     * 系统统一数据：单种sku允许添加到购物车的数额
     */
    Integer MAX = 10000;

    /**
     * 系统统一数据：允许添加到购物车sku种类的数额
     */
    Integer TYPE_MAX = 120;

    /**
     * 购物车每页显示条数（以淘宝为例，每次下拉都会刷新 31 条记录）
     */
//    Integer PAGE_SIZE = 16;
    Integer PAGE_SIZE = 4;

    /**
     * 为购物车加上前缀
     * @param userId
     * @return
     */
    static String getCartKey(Long userId) {
        return String.format(ShopCartConstant.SHOP_CART, userId);
    }

    static String getGroupingKey(Long userId) {
        return String.format(ShopCartConstant.SHOP_CART_FOR_GROUPING, userId);
    }

}
