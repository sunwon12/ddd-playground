package potato.server.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;
import potato.server.user.domain.User;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    private BigDecimal orderPrice;

    @Builder
    public Order(User user, Payment payment, BigDecimal orderPrice) {
        this.user = user;
        this.payment = payment;
        this.orderPrice = orderPrice;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
