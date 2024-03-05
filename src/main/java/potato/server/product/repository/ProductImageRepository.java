package potato.server.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.product.domain.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
