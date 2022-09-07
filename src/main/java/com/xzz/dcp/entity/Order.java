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
public class Order extends TimeLogEntity {
    /**
     * 订单 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    /**
     * 订单代码
     */
    private String code;

    /**
     * 订单类型
     */
    private Integer type;

    /**
     * 订单数量
     */
    private Float amount;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 邮费
     */
    private Float postage;

    /**
     * 重量（单位：克）
     */
    private Integer weight;
}
