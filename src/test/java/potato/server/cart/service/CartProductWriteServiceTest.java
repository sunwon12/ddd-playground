package potato.server.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.cart.domain.Cart;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.dto.request.CartProductCreateRequest;
import potato.server.cart.repository.CartProductRepository;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;
import potato.server.user.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

/**
 * @Author 허석문
 * @Since 2023-11-01
 */

@ExtendWith(MockitoExtension.class)
class CartProductWriteServiceTest {

    @InjectMocks
    CartProductWriteService sut;

    @Mock
    CartReadService cartReadService;
    @Mock
    CartWriteService cartWriteService;
    @Mock
    CartProductRepository cartProductRepository;
    @Mock
    ProductRepository productRepository;


    @DisplayName("상품이 이미 장바구니에 존재하고 수량을 증가")
    @Test
    void testAddToCartExistingProduct() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        CartProductCreateRequest request = new CartProductCreateRequest(productId, 2);

        User user = new User();
        Cart cart = Cart.builder().build();
        Product product = Product.builder().build();

        CartProduct existingCartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(3)
                .build();

        given(cartReadService.findCart(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(cartProductRepository.findCartProduct(cart.getId(), product.getId())).willReturn(Optional.of(existingCartProduct));

        // when
        sut.createCartProduct(userId, request);

        // then
        then(cartProductRepository).should().save(existingCartProduct);
        assertThat(existingCartProduct.getQuantity()).isEqualTo(5); // 수량확인
    }

    @DisplayName("상품이 장바구니에 없을때 추가")
    @Test
    void testAddToCartNewProduct() {
        // given
        Long userId = 1L;
        CartProductCreateRequest request = new CartProductCreateRequest(1L, 2);

        User user = new User();
        Cart cart = Cart.builder().build();
        Product product = Product.builder().build();

        // 사용자의 장바구니에 해당 상품이 없는 상황 모의
        given(cartReadService.findCart(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(cartProductRepository.findCartProduct(cart.getId(), product.getId())).willReturn(Optional.empty());

        // when
        sut.createCartProduct(userId, request);

        // then
        then(cartProductRepository).should().save(any(CartProduct.class));
    }

    @DisplayName("장바구니 상품을 업데이트 한다.")
    @Test
    void testUpdateQuantity() {
        // given
        Long userId = 1L;
        Long cartProductId = 2L;
        int updateQuantity = 5;
        Cart cart = Cart.builder().build();
        Product product = Product.builder().build();

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(3)
                .build();


        given(cartReadService.findCart(userId)).willReturn(Optional.of(cart));
        given(cartProductRepository.findById(cartProductId)).willReturn(Optional.of(cartProduct));

        // when
        sut.updateQuantity(userId, cartProductId, updateQuantity);

        // then
        then(cartProductRepository).should(times(1)).save(any(CartProduct.class));
        assertThat(cartProduct.getQuantity()).isEqualTo(3 + updateQuantity);
    }

    @DisplayName("장바구니 상품을 삭제한다")
    @Test
    void testDeleteCartProduct() {
        // 가상의 사용자 ID와 카트 상품 ID를 설정
        Long userId = 1L;
        Long cartProductId = 123L;

        // 가상의 Cart 객체 생성
        Cart cart = Cart.builder().build();

        // 가상의 CartProduct 객체 생성
        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .build();

        // given: Mockito를 사용하여 Mock 객체에 대한 행동 설정
        given(cartReadService.findCart(userId)).willReturn(Optional.of(cart));
        given(cartProductRepository.findById(cartProductId)).willReturn(Optional.of(cartProduct));

        // when: 테스트 대상 메서드 호출
        sut.deleteCartProduct(userId, cartProductId);

        // then: Mockito를 사용하여 delete 메서드가 호출되었는지 확인
        then(cartProductRepository).should(times(1)).delete(cartProduct);
    }

}
