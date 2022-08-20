package com.xzz.dcp.service;

import com.xzz.dcp.ApplicationTests;
import com.xzz.dcp.dto.ShopCartSkuDTO;
import com.xzz.dcp.entity.Sku;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ISkuServiceTest extends ApplicationTests {

    @Autowired
    private ISkuService skuService;

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