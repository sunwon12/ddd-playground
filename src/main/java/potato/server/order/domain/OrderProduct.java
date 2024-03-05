package potato.server.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;
import potato.server.product.domain.Product;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class OrderProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

    private BigDecimal price;

    @Builder
    public OrderProduct(Product product, Order order, int count, BigDecimal price) {
        this.product = product;
        this.order = order;
        this.count = count;
        this.price = price;
    }
}
