package potato.server.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.product.domain.Product;

/**
 * @author: 박건휘
 * @since: 2023-09-28
 */
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}