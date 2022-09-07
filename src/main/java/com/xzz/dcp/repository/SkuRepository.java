package com.xzz.dcp.repository;

import com.xzz.dcp.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {

    /**
     * 查找未删除的 sku
     *
     * @param id     sku ID
     * @param status sku 的状态
     * @return sku 对象 {@link Sku}
     */
    Sku findBySkuIdAndStatusIsNot(Long id, Integer status);
}
