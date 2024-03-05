package potato.server.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.product.domain.Category;

/**
 * @author: 박건휘
 * @since: 2023-11-13
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}