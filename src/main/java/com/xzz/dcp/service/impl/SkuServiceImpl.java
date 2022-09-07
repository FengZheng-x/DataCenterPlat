package com.xzz.dcp.service.impl;

import com.xzz.dcp.common.enums.ResponseEnum;
import com.xzz.dcp.common.enums.StatusEnum;
import com.xzz.dcp.service.ex.ValidateException;
import com.xzz.dcp.dto.ShopCartSkuDTO;
import com.xzz.dcp.entity.Sku;
import com.xzz.dcp.repository.SkuRepository;
import com.xzz.dcp.service.ISkuService;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkuServiceImpl implements ISkuService {

    private final SkuRepository skuRepository;

    public SkuServiceImpl(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    /**
     * sku 详情
     * 这里的缓存在更新或者删除该 sku 的时候清理。
     * 为什么这里查找的是上架和下架两个状态的 sku。因为下架的 sku 也需要展示
     *
     * @param id sku ID
     * @return sku 对象 {@link Sku}
     */
    @Override
    @Cacheable(cacheNames = "entity:sku", key = "#id")
    public Sku getById(Long id) {
        return Optional.ofNullable(skuRepository.findBySkuIdAndStatusIsNot(id, StatusEnum.DELETED.getCode()))
                .orElseThrow(() -> new ValidateException(ResponseEnum.SKU_IS_NOT_EXISTS));
    }

    /**
     * 用于展示的数据。如果还涉及到其他表的数据，可在这里封装。
     * 这里的缓存在更新或者删除该 sku 的时候清理
     *
     * @param id sku ID
     * @return 购物车对象 {@link ShopCartSkuDTO}
     */
    @Override
    public ShopCartSkuDTO getShopCartSkuById(Long id) {
        Sku sku = proxy().getById(id);
        return ShopCartSkuDTO.of(sku);
    }

    private ISkuService proxy() {
        return (SkuServiceImpl) AopContext.currentProxy();
    }

}
