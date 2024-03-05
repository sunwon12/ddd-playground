package potato.server.product.repository;

import potato.server.product.dto.response.ProductResponse;
import java.util.List;

/**
 * @author: 박건휘
 * @since: 2024-01-14
 */
public interface ProductRepositoryCustom {
    List<ProductResponse> findAllProducts();
}
