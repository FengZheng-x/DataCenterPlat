package com.xzz.service;

import com.xzz.dto.ShopCartSkuDTO;
import com.xzz.entity.Sku;
import com.xzz.service.SkuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SkuServiceTest{
    @Autowired
    private SkuService skuService;

    /**
     * 测试缓存
     */
    @Test
    public void getById() {
        Sku sku = skuService.getById(1L);
        System.out.println(sku);
    }

    @Test
    public void getShopCartSkuById() {
        ShopCartSkuDTO dto = skuService.getShopCartSkuById(1L);
        System.out.println(dto);
    }
}
