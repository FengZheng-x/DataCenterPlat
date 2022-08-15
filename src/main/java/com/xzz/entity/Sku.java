package com.xzz.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaDeserializers.LocalDateTimeDeserializer;
import org.codehaus.jackson.map.ext.JodaSerializers.LocalDateTimeSerializer;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 商品模块
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Accessors(chain = true) // 链式调用
public class Sku implements Serializable {
    /**
     * SKU ID
     */
    @Id // 声明一个实体类的属性映射为数据库的主键列
    // 主键的生成策略，采用数据库 ID 自增的方式来生成主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 店铺 ID
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
     * 商品单价（单位：元）
     */
    private float price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 规格
     */
    private String properties;

    /**
     * 单种 sku 允许添加到购物车中的最大数量
     */
    private Integer maxNum;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * sku 状态：-1 下架; 0 删除; 1 在架
     */
    private Integer status;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Sku sku = (Sku) o;
        return Objects.equals(id, sku.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static void main(String[] args) {
        Sku sku = new Sku();
        sku.setPrice(100F).setShopId(123L).setShopName("MacroHard").setMaxNum(100);
        System.out.println(sku);
    }
}
