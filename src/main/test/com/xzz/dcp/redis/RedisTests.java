package com.xzz.dcp.redis;

import com.xzz.dcp.DCPApplicationTests;
import com.xzz.dcp.common.constants.ShopCartConstant;
import com.xzz.dcp.common.enums.StatusEnum;
import com.xzz.dcp.dto.RedisTestDTO;
import com.xzz.dcp.entity.Sku;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.*;

/**
 * 执行完放可以输入 redis 命令 > object encoding [key] 来查看底层数据结构
 */
public class RedisTests extends DCPApplicationTests {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    private Sku sku;

    private RedisTestDTO redisTestDTO;

    @Before
    public void before() {
        Date date = new Date();
        sku = new Sku();
        sku.setItemId(111111L)
                .setShopId(123457L)
                .setShopName("MiCai Shop")
                .setItemName("帆布鞋")
                .setPrice(150F)
                .setStock(100)
                .setPicture("http://...pic...123.png")
                .setProperties("白色;40")
                .setMax(300)
                .setStatus(StatusEnum.NORMAL.getCode())
                .setCreateTime(date)
                .setUpdateTime(date);

        redisTestDTO = new RedisTestDTO()
                .setSkuId(Long.MAX_VALUE)
                .setCount(ShopCartConstant.MAX);
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

    @Test
    public void shopCartTest1() {
        ArrayList<Sku> list = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            list.add(sku);
        }
        ValueOperations<String, Serializable> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("shopCart120", list);
    }

    @Test
    public void scanTest() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        // 设置 count 选项来指定每次迭代返回元素的最大值，设置 match 指定需要匹配的 pattern
        Cursor<Map.Entry<Object, Object>> cursor = hashOperations.scan("SHOP_CART:999999",
                ScanOptions.scanOptions().count(120).match("*").build());
        // 带顺序的 HashMap，同一个元素就算被返回多次也不影响。
        Map<Object, Object> linkedHashMap = new LinkedHashMap<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        cursor.close();
        System.out.println(linkedHashMap);
    }

}
