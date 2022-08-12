package repository;

import entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    /**
     * 查找未删除的Sku
     *
     * @param id     sku id
     * @param status sku 状态
     * @return {@link Sku} 对象
     */
    Sku findByIdAndStatusIsNot(Long id, Integer status);
}
