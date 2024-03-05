package potato.server.cart.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import potato.server.cart.domain.CartProduct;

import java.util.List;
import java.util.Optional;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query("SELECT cp FROM CartProduct cp WHERE cp.cart.id = :cartId AND cp.product.id = :productId")
    Optional<CartProduct> findCartProduct(@Param("cartId") Long cartId, @Param("productId") Long productId);

    @EntityGraph(attributePaths = {"product"})
    List<CartProduct> findAllByCartId(@Param("cartId") Long cartId);
}
