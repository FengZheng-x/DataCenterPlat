package service;

import dto.ShopCartDTO;

import java.util.List;

/**
 * 购物车服务
 */
public interface ShopCartService {
    /**
     * 改变指定的 sku 的数量
     *
     * @param userId 用户 ID
     * @param skuId  sku ID
     * @param value  该 sku 的数量
     */
    void put(long userId, long skuId, long value);

    /**
     * 购物车列表分页
     *
     * @param userId      用户 ID
     * @param currentPage 购物车当前页
     * @return 当前页的购物车内容
     */
    List<ShopCartDTO> shopCarts(long userId, int currentPage);

    /**
     * 购物车列表
     *
     * @param userId 用户 ID
     * @param skuIds sku ID
     * @return 根据 sku ID 返回对应的购物车列表
     */
    List<ShopCartDTO> shopCarts(long userId, List<Long> skuIds);

    /**
     * 移除购物车只能的 sku
     *
     * @param userId 用户 ID
     * @param skuIds sku ID数组（该参数必须是可变参数或者数组，后面需要转为 byte[][] 类型，
     *               如果用集合的话后面序列化的 byte[][] 结果是错的）
     */
    void remove(long userId, long... skuIds);

    /**
     * 清空购物车
     *
     * @param userId 用户 ID
     */
    void clear(long userId);

    /**
     * 进入购物车
     *
     * @param userId 用户 ID
     * @return 购物车完整页面
     */
    int into(long userId);

    /**
     * 获取当前页的 sku IDs
     *
     * @param userId 用户 ID
     * @param currentPage 当前页
     * @return 当前页的 sku ID 列表
     */
    List<Long> currentPageSkuIds(long userId, int currentPage);

    /**
     * 获得分组后的购物车
     *
     * @param userId 用户 ID
     * @return 分组后的购物车列表
     */
    List<Long> shopCartGrouping(long userId);
}
