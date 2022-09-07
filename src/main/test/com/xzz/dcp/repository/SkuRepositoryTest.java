package com.xzz.dcp.repository;

import com.xzz.dcp.DCPApplicationTests;
import com.xzz.dcp.common.enums.StatusEnum;
import com.xzz.dcp.entity.Sku;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SkuRepositoryTest extends DCPApplicationTests {

    @Autowired
    private SkuRepository skuRepository;

    @Test
    public void saveTest() {
        Date date = new Date();
        Sku sku = new Sku();
                sku.setItemId(151312L)
                .setShopId(123457L)
                .setShopName("MiCai Shop")
                .setItemName("胸针")
                .setPrice(1910F)
                .setStock(1000)
                .setPicture("http://...pic...123.png")
                .setProperties("骑士红")
                .setMax(10)
                .setStatus(StatusEnum.PROHIBIT.getCode())
                .setCreateTime(date)
                .setUpdateTime(date);
        skuRepository.save(sku);
    }

    @Test
    public void findByIdTest() {
        Sku sku = skuRepository.findById(1L)
                .orElseThrow(RuntimeException::new);
        System.out.println(sku);
    }

    @Test
    public void findByIdAndStatusIsNotTEst() {
        Sku sku = skuRepository.findBySkuIdAndStatusIsNot(5L, StatusEnum.DELETED.getCode());
        System.out.println(sku);
    }

}