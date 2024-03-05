package potato.server.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import potato.server.product.dto.response.ProductResponse;
import potato.server.product.dto.response.QProductResponse;
import java.util.List;
import static potato.server.product.domain.QProduct.product;

/**
 * @author: 박건휘
 * @since: 2024-01-14
 */

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 제품 목록, 최신순 대로 정렬
     */
    public List<ProductResponse> findAllProducts() {
        return queryFactory
                .select(new QProductResponse(
                        product.title,
                        product.price,
                        product.description,
                        product.hit,
                        product.stock,
                        product.version
                ))
                .from(product)
                .orderBy(product.createdDate.asc())
                .fetch();
    }
}