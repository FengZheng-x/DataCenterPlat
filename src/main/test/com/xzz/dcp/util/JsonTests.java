package com.xzz.dcp.util;

import com.xzz.dcp.DCPApplicationTests;
import com.xzz.dcp.common.enums.StatusEnum;
import com.xzz.dcp.common.util.JsonUtil;
import com.xzz.dcp.entity.Sku;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class JsonTests extends DCPApplicationTests {

    private Sku sku;

    @Before
    public void before() {
        Date date = new Date();
        sku = new Sku();
        sku.setItemId(111111L)
                .setShopId(123457L)
                .setShopName("MiCai Shop")
                .setItemName("帆布鞋")
                .setPrice(15000F)
                .setStock(100)
                .setPicture("http://...pic...123.png")
                .setProperties("白色;40")
                .setMax(300)
                .setStatus(StatusEnum.NORMAL.getCode())
                .setCreateTime(date)
                .setUpdateTime(date);
    }

    @Test
    public void fun() {
        String json = JsonUtil.toJson(this.sku);
        System.out.println(json);
        Sku sku = JsonUtil.fromJson(json, Sku.class);
        System.out.println(sku);
        System.out.printf("Create time: %s%n", sku.getCreateTime());
    }


}
