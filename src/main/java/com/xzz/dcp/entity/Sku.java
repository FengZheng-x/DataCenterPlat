package com.xzz.dcp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * 全名 Stock Keeping Unit(库存量最小单位)，可以理解为有具体规格的商品。
 * 例如：40码白色帆布鞋。
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Accessors(chain = true)
public class Sku extends TimeLogEntity {

    /**
     * skuID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 这里为了方便，用自增
    private Long id;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称（冗余字段）
     */
    private String shopName;

    /**
     * 商品名称（冗余字段）
     */
    private String itemName;

    /**
     * sku单价（单位：元）
     */
    private Float price;

    /**
     * sku库存
     */
    private Integer stock;

    /**
     * sku图片
     */
    private String picture;

    /**
     * 规格（规格本来应该是张表的，这里为了方便，就直接 String）
     */
    private String properties;

    /**
     * 单种sku允许添加到购物车的最大数额
     */
    private Integer max;


    /**
     * sku状态
     * -1 下架
     * 0 删除
     * 1 在架
     */
    private Integer status;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Sku sku = (Sku) o;
        return id != null && Objects.equals(id, sku.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
