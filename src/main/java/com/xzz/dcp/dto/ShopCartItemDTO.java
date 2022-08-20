package com.xzz.dcp.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车skuDTO，去掉了重复的属性
 */
@Data
@Accessors(chain = true)
public class ShopCartItemDTO {

    /**
     * skuID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * sku单价（单位：元）
     */
    private Float price;

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
     * sku个数
     */
    private Integer count;

    /**
     * sku状态：-1 下架; 0 删除; 1 在架
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
