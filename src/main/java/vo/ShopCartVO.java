package vo;

import dto.ShopCartDTO;
import dto.ShopCartSkuDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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

    private static ShopCartDTO of(ShopCartSkuDTO shopCartSkuDTO) {
        return new ShopCartDTO()
                .setShopId(shopCartSkuDTO.getShopId())
                .setShopName(shopCartSkuDTO.getShopName());
    }
}
