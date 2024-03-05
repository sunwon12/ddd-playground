package potato.server.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.order.domain.Order;

import java.util.Optional;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"payment"})
    Optional<Order> findOrderById(Long aLong);
}
