package potato.server.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.Cart;
import potato.server.cart.dto.response.CartResponse;
import potato.server.cart.repository.CartRepository;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartReadService {

    private final CartRepository cartRepository;
    private final CartProductReadService cartProductReadService;
    private final CartWriteService cartWriteService;


    public Optional<Cart> findCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartResponse findCartInfo(Long userId) {
        Cart cart = findCart(userId).orElse(
                cartWriteService.createCart(userId)
        );

        return CartResponse.of(cart.getId(), cartProductReadService.findCartProductList(cart.getId()));
    }
}
