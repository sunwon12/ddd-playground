package potato.server.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.product.domain.Wishlist;
import java.util.List;

/**
 * @author: 박건휘
 * @since: 2023-11-25
 */

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUserId(Long userId);
}