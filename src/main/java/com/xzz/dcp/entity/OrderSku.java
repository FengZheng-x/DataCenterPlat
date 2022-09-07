package com.xzz.dcp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Accessors(chain = true)
public class OrderSku {
    /**
     * 自增 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单 ID
     */
    private Long orderId;

    /**
     * sku ID
     */
    private Long skuId;

    /**
     * 商品价格
     */
    private Float price;

    /**
     * 商品数量
     */
    private Integer num;
}
