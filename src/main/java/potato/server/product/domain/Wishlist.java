package potato.server.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import potato.server.common.BaseTimeEntity;
import potato.server.user.domain.User;

/**
 * @author 박건휘
 * @since 2023-08-09
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Builder
    public Wishlist(Product product, User user) {
        this.product = product;
        this.user = user;
    }
}