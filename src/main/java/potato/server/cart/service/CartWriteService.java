package potato.server.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.Cart;
import potato.server.cart.repository.CartRepository;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartWriteService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));

        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }
}
