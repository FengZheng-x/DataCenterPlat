package com.xzz.service.impl;

import com.xzz.common.util.JsonUtil;
import com.xzz.dto.ShopCartDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.xzz.service.ShopCartService;

import java.util.List;

public class ShopCartServiceImplTest {
    private Long userId = 999999L;

    @Autowired
    private ShopCartService shopCartService;

    @Test
    public void incrementOne() {
        shopCartService.put(userId, 1L, 101L);
    }

    @Test
    public void init() {
        shopCartService.put(userId, 2L, 100L);
        shopCartService.put(userId, 4L, 100L);
        shopCartService.put(userId, 9L, 100L);
        shopCartService.put(userId, 3L, 100L);
        shopCartService.put(userId, 8L, 100L);
        shopCartService.put(userId, 12L, 100L);
        shopCartService.put(userId, 1L, 100L);
        shopCartService.put(userId, 6L, 100L);
        shopCartService.put(userId, 10L, 100L);
        shopCartService.put(userId, 11L, 100L);
        shopCartService.put(userId, 7L, 100L);
    }

    @Test
    public void shopCarts() {
        List<ShopCartDTO> shopCartDTOs = shopCartService.shopCarts(userId, 3);
        String json = JsonUtil.toJson(shopCartDTOs);
        System.out.println(json);
    }

    @Test
    public void delete() {
        Long[] skuIds = new Long[]{7L, 10L, 2L};
        shopCartService.remove(userId, skuIds);
    }

    @Test
    public void clear() {
        shopCartService.clear(userId);
    }

    @Test
    public void into() {
        Integer totalPage = shopCartService.into(userId);
        System.out.println(totalPage);
    }

    @Test
    public void shopCartGrouping() {
        List<Long> list = shopCartService.shopCartGrouping(userId);
        System.out.println(list);
    }

}
