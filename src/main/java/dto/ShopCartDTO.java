package dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ShopCartDTO {
    /**
     * 店铺 ID
     */
    private long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 购物车商品集合
     */
    private List<ShopCartItemDTO> items;

    public static ShopCartDTO of(ShopCartSkuDTO shopCartSKUDTO) {
        return new ShopCartDTO()
                .setShopId(shopCartSKUDTO.getShopId())
                .setShopName(shopCartSKUDTO.getShopName());
    }
}
