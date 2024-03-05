package potato.server.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.dto.CartProductParam;
import potato.server.cart.repository.CartProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductReadService {

    private final CartProductRepository cartProductRepository;

    public List<CartProductParam> findCartProductList(Long cartId) {
        List<CartProduct> cartProductList = cartProductRepository.findAllByCartId(cartId);

        if (cartProductList.isEmpty()) {
            return List.of();
        }

        return cartProductList
                .stream().map(CartProductParam::from)
                .toList();
    }
}
