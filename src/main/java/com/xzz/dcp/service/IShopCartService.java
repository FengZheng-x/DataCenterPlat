package com.xzz.dcp.service;

import com.xzz.dcp.dto.ShopCartDTO;

import java.util.List;

/**
 * 购物车服务
 */
public interface IShopCartService {

    /**
     * 改变指定 sku 的 count
     *
     * @param userId     用户 ID
     * @param skuId      skuID
     * @param finalValue count 的最终值
     */
    void put(Long userId, Long skuId, Integer finalValue);

    /**
     * 购物车列表分页
     *
     * @param userId      用户 ID
     * @param currentPage 当前页数
     * @return 购物车当前页列表
     */
    List<ShopCartDTO> shopCarts(Long userId, Integer currentPage);

    /**
     * 购物车列表
     *
     * @param userId 用户 ID
     * @param skuIds sku ID 列表
     * @return 购物车列表
     */
    List<ShopCartDTO> shopCarts(Long userId, List<Long> skuIds);

    /**
     * 移除购物车指定 sku
     *
     * @param userId 用户 ID
     * @param skuIds skuID 数组（该参数必须是可变参数或者数组，后面需要转为 byte[][] 类型，如果
     *               这里用集合的话，后面序列化的 byte[][] 结果是错的）
     */
    void remove(Long userId, Long... skuIds);

    /**
     * 清空购物车
     *
     * @param userId 用户 ID
     */
    void clear(Long userId);

    /**
     * 进入购物车
     *
     * @param userId 用户 ID
     * @return 购物车页数
     */
    Integer into(Long userId);

    /**
     * 获得当前页的 skuIds
     *
     * @param userId      用户 ID
     * @param currentPage 当前页数
     * @return 指定页数的购物车列表
     */
    List<Long> currentPageSkuIds(Long userId, Integer currentPage);

    /**
     * 获得分组摊平后的购物车
     *
     * @param userId 用户 ID
     * @return 分组后的购物车列表
     */
    List<Long> shopCartGrouping(Long userId);

}
