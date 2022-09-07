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
public class ShopCart extends TimeLogEntity{
    /**
     * 购物车 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * skuID
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 勾选状态（0 表示未勾选，1 表示勾选）
     */
    private Integer selected;
}
