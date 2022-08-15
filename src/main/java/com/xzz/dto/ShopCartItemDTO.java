package com.xzz.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车 itemDTO，去掉了重复的属性
 */
@Data
@Accessors(chain = true)
public class ShopCartItemDTO {
    /**
     * sku ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * sku 单价（单位：元）
     */
    private Float price;

    /**
     * 规格
     */
    private String properties;

    /**
     * 单种 sku 允许添加到购物车的最大数额
     */
    private Integer maxNum;

    /**
     * sku 个数
     */
    private Integer count;

    /**
     * sku 状态：-1 下架; 0 删除; 1 在架
     */
    private Integer status;

    private static ShopCartItemDTO of(ShopCartSkuDTO shopCartSkuDTO) {
        ShopCartItemDTO shopCartItemDTO = new ShopCartItemDTO();
        BeanUtils.copyProperties(shopCartSkuDTO, shopCartItemDTO);
        return shopCartItemDTO;
    }

    public static List<ShopCartItemDTO> of(List<ShopCartSkuDTO> shopCartSkuDTOs) {
        return shopCartSkuDTOs.stream()
                .map(ShopCartItemDTO::of)
                .collect(Collectors.toList());
    }
}
