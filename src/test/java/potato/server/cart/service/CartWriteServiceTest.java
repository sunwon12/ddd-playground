package potato.server.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.cart.domain.Cart;
import potato.server.cart.repository.CartRepository;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


/**
 * @Author 허석문
 * @Since 2023-10-25
 */
@ExtendWith(MockitoExtension.class)
class CartWriteServiceTest {

    @InjectMocks
    CartWriteService cartWriteService;

    @Mock
    CartRepository cartRepository;
    @Mock
    UserRepository userRepository;


    @DisplayName("새로운 사용자의 장바구니를 생성")
    @Test
    void testCreateCart() {
        // given
        Long userId = 1L;
        User user = new User();
        Cart cart = Cart.builder().build();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(cartRepository.save(any(Cart.class))).willReturn(cart);

        // when
        Cart result = cartWriteService.createCart(userId);

        // then
        then(cartRepository).should().save(any(Cart.class));
        assertThat(result).isNotNull().isEqualTo(cart);
    }
}
