package com.xzz.dcp.mapper;

import com.xzz.dcp.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    /**
     * 插入订单数据
     *
     * @param order 订单数据
     * @return 受影响的行数（根据返回值判断是否插入成功）
     */
    Integer insert(Order order);

    /**
     * 根据订单代码查询订单数据
     *
     * @param code 订单代码
     * @return 如果找到对应的订单则返回这个订单数据，如果没有找到则返回 null
     */
    Order findByCode(String code);
}
