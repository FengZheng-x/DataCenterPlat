package com.xzz.vo;

import com.xzz.dto.ShopCartDTO;
import com.xzz.dto.ShopCartSkuDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 店铺 VO，包含了`店铺`信息和`购物车 sku`信息
 */
@Data
@Accessors(chain = true)
public class ShopCartVO {
    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 购物车sku集合
     */
    private List<ShopCartItemVO> items;

    private static ShopCartVO of(ShopCartDTO shopCartDTO) {
        return new ShopCartVO()
                .setShopId(shopCartDTO.getShopId())
                .setShopName(shopCartDTO.getShopName())
                .setItems(ShopCartItemVO.of(shopCartDTO.getItems()));
    }

    public static List<ShopCartVO> of(List<ShopCartDTO> shopCartDTOs) {
        return CollectionUtils.isEmpty(shopCartDTOs) ?
                new ArrayList<>() :
                shopCartDTOs.stream()
                        .map(ShopCartVO::of)
                        .collect(Collectors.toList());
    }
}
