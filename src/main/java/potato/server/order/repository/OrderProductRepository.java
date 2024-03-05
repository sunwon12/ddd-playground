package potato.server.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import potato.server.order.domain.OrderProduct;

import java.util.List;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT COUNT(op) > 0 FROM OrderProduct op WHERE op.product.id = :productId AND op.order.user.id = :userId")
    boolean existsByProductAndUser(@Param("productId") Long productId, @Param("userId") Long userId);

    @EntityGraph(attributePaths = {"product"})
    List<OrderProduct> findOrderProductListByOrderId(Long orderId);
}
