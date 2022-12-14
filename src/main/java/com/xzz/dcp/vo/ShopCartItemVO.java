package com.xzz.dcp.vo;

import com.xzz.dcp.dto.ShopCartItemDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车 skuVO，去掉了重复的属性
 */
@Data
@Accessors(chain = true)
public class ShopCartItemVO {

    /**
     * skuID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

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
     * 前端用于判断，不给用户随便输入
     */
    private Integer max;

    /**
     * sku个数
     */
    private Integer count;

    /**
     * sku状态：-1 下架; 0 删除; 1 在架
     * 前端用于判断，下架的 sku 不允许移出购物车（count 减1），只给用户移除整个 sku。
     */
    private Integer status;

    private static ShopCartItemVO of(ShopCartItemDTO shopCartItemDTO) {
        ShopCartItemVO shopCartItemVO = new ShopCartItemVO();
        BeanUtils.copyProperties(shopCartItemDTO, shopCartItemVO);
        return shopCartItemVO;
    }

    public static List<ShopCartItemVO> of(List<ShopCartItemDTO> shopCartItemDTOs) {
        return shopCartItemDTOs.stream()
                .map(ShopCartItemVO::of)
                .collect(Collectors.toList());
    }

}
