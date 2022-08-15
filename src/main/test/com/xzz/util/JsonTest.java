package com.xzz.util;

import com.xzz.common.enums.StatusEnum;
import com.xzz.common.util.JsonUtil;
import com.xzz.entity.Sku;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class JsonTest{
    private Sku sku;

    @Before
    public void before() {
        sku = new Sku()
                .setItemId(111111L)
                .setShopId(123457L)
                .setShopName("MiCai Shop")
                .setItemName("帆布鞋")
                .setPrice(15000L)
                .setStock(100)
                .setProperties("白色;40")
                .setMaxNum(300)
                .setStatus(StatusEnum.NORMAL.getCode())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
    }

    @Test
    public void fun() {
        String json = JsonUtil.toJson(this.sku);
        System.out.println(json);
        Sku sku = JsonUtil.fromJson(json, Sku.class);
        System.out.println(sku);
    }
}
