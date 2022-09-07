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
public class OrderPay {
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
     * 支付 ID
     */
    private Long payId;

    /**
     * 订单支付状态
     */
    private Integer payStatus;
}
