package com.xzz.redis;

import com.xzz.common.constants.ShopCartConstant;
import com.xzz.dto.RedisTestDTO;
import com.xzz.entity.Sku;
import com.xzz.common.enums.StatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 执行完放可以输入 redis 命令 > object encoding [key] 来查看底层数据结构
 */
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    private Sku sku;

    private RedisTestDTO redisTestDTO;

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

        redisTestDTO = new RedisTestDTO()
                .setSkuId(Long.MAX_VALUE)
                .setCount(ShopCartConstant.ITEM_MAX);
    }

    @Test
    public void zSetTest() {
        String key = "ZSET_TEST";
        ZSetOperations<String, Serializable> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, redisTestDTO, 1);   // skiplist
        Set<Serializable> range = zSetOperations.range(key, 0, -1);
        assert range != null;
        range.forEach(System.out::println);
    }

    @Test
    public void hashTest() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("Cart", "用户id", sku);
        Sku result = (Sku) hashOperations.get("Cart", "用户id");
        System.out.println(result);
    }

    @Test
    public void stringTest() {
        ValueOperations<String, Serializable> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("SKU", sku);
        Serializable sku = valueOperations.get("SKU");
        System.out.println(sku);
    }

    @Test
    public void listTest() {
        String key = "LIST_TEST";
        ListOperations<String, Serializable> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, redisTestDTO);
        List<Serializable> range = listOperations.range(key, 0, -1);
        assert range != null;
        range.forEach(System.out::println);
    }
}
