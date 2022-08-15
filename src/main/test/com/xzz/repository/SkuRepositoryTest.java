package com.xzz.repository;

import com.xzz.common.enums.StatusEnum;
import com.xzz.entity.Sku;
import com.xzz.repository.SkuRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class SkuRepositoryTest{
    @Autowired
    private SkuRepository skuRepository;

    @Test
    public void saveTest() {
        Sku sku = new Sku()
                .setItemId(151312L)
                .setShopId(123457L)
                .setShopName("MiCai Shop")
                .setItemName("胸针")
                .setPrice(191000L)
                .setStock(1000)
                .setProperties("骑士红")
                .setMaxNum(10)
                .setStatus(StatusEnum.PROHIBIT.getCode())
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
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
        Sku sku = skuRepository.findByIdAndStatusIsNot(5L, StatusEnum.DELETED.getCode());
        System.out.println(sku);
    }
}
