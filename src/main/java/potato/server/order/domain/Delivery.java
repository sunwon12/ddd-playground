package potato.server.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderProduct orderProduct;

    @Column(length = 50, nullable = false)
    private String deliveryStatus;

    private int orderNumber;

    @Column(length = 100)
    private String shipper;

    @Builder
    public Delivery(OrderProduct orderProduct, String deliveryStatus, int orderNumber, String shipper) {
        this.orderProduct= orderProduct;
        this.deliveryStatus = deliveryStatus;
        this.orderNumber = orderNumber;
        this.shipper = shipper;
    }
}
