package com.xzz.service.impl;

import com.xzz.service.SkuService;
import com.xzz.common.enums.ResponseEnum;
import com.xzz.common.enums.StatusEnum;
import com.xzz.common.exception.ValidateException;
import com.xzz.dto.ShopCartSkuDTO;
import com.xzz.entity.Sku;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.xzz.repository.SkuRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Service
public class SkuServiceImpl implements SkuService {
    private final SkuRepository skuRepository;

    public SkuServiceImpl(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    /**
     * 这里的缓存在更新或者删除该 sku 的时候清理。
     * 为什么这里查找的是上架和下架两个状态的 sku。因为下架的 sku 也需要展示
     *
     * @param id sku ID
     * @return sku 详情
     */
    @Override
    @Cacheable(value = "entity:sku", key = "#id")
    public Sku getById(Long id) {
        return Optional.ofNullable(skuRepository.findByIdAndStatusIsNot(id, StatusEnum.DELETED.getCode()))
                .orElseThrow(() -> new ValidateException(ResponseEnum.SKU_NOT_EXISTS));
    }

    /**
     * 用于展示的数据。如果还涉及到其他表的数据，可在这里封装。
     * 这里的缓存在更新或者删除该 sku 的时候清理
     *
     * @param id sku ID
     * @return {@link ShopCartSkuDTO} 对象
     */
    @Override
    public ShopCartSkuDTO getShopCartSkuById(Long id) {
        Sku sku = proxy().getById(id);
        return ShopCartSkuDTO.of(sku);
    }

    private SkuService proxy() {
        return (SkuServiceImpl) AopContext.currentProxy();
    }
}
