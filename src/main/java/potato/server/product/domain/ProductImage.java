package potato.server.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;

/**
 * @author 박건휘
 * @since 2023-08-09
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_image")
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 500, nullable = false)
    private String link;

    @Builder
    public ProductImage(Product product, String link) {
        this.product = product;
        this.link = link;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
