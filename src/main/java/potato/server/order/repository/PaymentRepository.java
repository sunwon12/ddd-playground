package potato.server.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.order.domain.Payment;

/**
 * @author
 * @since
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
