package com.xzz.service;

import com.xzz.dto.ShopCartSkuDTO;
import com.xzz.entity.Sku;

public interface SkuService {
    /**
     * 根据 skuID 查找 sku
     * @param id sku ID
     * @return {@link Sku} 对象
     */
    Sku getById(Long id);

    /**
     * 根据 skuID 查询购物车 skuDTO
     * @param id sku ID
     * @return {@link ShopCartSkuDTO} 对象
     */
    ShopCartSkuDTO getShopCartSkuById(Long id);
}
