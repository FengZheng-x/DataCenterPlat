package commons.constants;

public interface ShopCartConstant {
    /**
     * 购物车key前缀
     */
    String SHOP_CART = "SHOPCART:%s";

    /**
     * 用于分组的购物车（后续需要按店铺分组）
     */
    String SHOP_CART_FOR_GROUPING = "SHOPCART:GROUPING:%s";

    /**
     * 排序购物车
     */
    String SHOP_CART_SORT = "SHOPCART:SORT";

    /**
     * 购物车分页（根据排序购物车计算出的分页）
     */
    String SHOP_CART_PAGE = "SHOPCART:PAGE";

    /**
     * 系统统一数据：单种商品允许添加到购物车的数量
     */
    Integer SHOP_CART_MAX = 10000;

    /**
     * 系统统一数据：允许添加到购物车商品种类的数额
     */
    Integer ITEM_MAX = 120;

    /**
     * 购物车每页显示条数
     */
    Integer PAGE_SIZE = 10;

    /**
     * 为购物车加上前缀，每位用户拥有唯一的购物车
     *
     * @param userId 用户 id
     * @return 格式化后的购物车 key 值
     */
    static String getCartKey(Long userId) {
        return String.format(ShopCartConstant.SHOP_CART, userId);
    }

    static String getGroupingKey(Long userId) {
        return String.format(ShopCartConstant.SHOP_CART_FOR_GROUPING, userId);
    }

}
