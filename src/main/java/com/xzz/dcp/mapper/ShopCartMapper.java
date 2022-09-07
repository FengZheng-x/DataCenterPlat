package com.xzz.dcp.mapper;

import com.xzz.dcp.entity.ShopCart;
import com.xzz.dcp.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartMapper {
    /**
     * 插入购物车数据
     *
     * @param shopCart 购物车数据
     * @return 受影响的行数（增删改都将受影响的行数作为返回值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(ShopCart shopCart);

    /**
     * 根据用户 ID 来查询购物车数据
     *
     * @param userId 用户 ID
     * @return 如果找到对应的用户则返回这个用户的数据，如果没有找到则返回 null
     */
    User findByUsername(Long userId);
}
