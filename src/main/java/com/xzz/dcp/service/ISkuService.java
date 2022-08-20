package com.xzz.dcp.service;

import com.xzz.dcp.dto.ShopCartSkuDTO;
import com.xzz.dcp.entity.Sku;

public interface ISkuService {

    /**
     * 根据 skuID 查找 sku
     *
     * @param id sku ID
     * @return sku 对象 {@link Sku}
     */
    Sku getById(Long id);

    /**
     * 根据 skuID 查询购物车 skuDTO
     *
     * @param id sku ID
     * @return 购物车对象 {@link ShopCartSkuDTO}
     */
    ShopCartSkuDTO getShopCartSkuById(Long id);

}
