package potato.server.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.cart.domain.Cart;
import potato.server.cart.repository.CartRepository;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static potato.server.fixture.CartFixture.createCart;

@DisplayName("장바구니 서비스 테스트 - 읽기")
@ExtendWith(MockitoExtension.class)
class CartReadServiceTest {

    @InjectMocks
    CartReadService sut;

    @Mock
    CartRepository cartRepository;

    @DisplayName("사용자의 장바구니를 반환")
    @Test
    void testFindCart() throws Exception{
        // given
        Long userId = 1L;
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(createCart(1L)));

        // when
        Optional<Cart> result = sut.findCart(userId);

        // then
        then(cartRepository).should().findByUserId(userId);
        // assertThat(result).contains(cart);
    }

}
