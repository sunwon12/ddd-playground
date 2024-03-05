package potato.server.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.Cart;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.dto.request.CartProductCreateRequest;
import potato.server.cart.repository.CartProductRepository;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;

/**
 * @Author 허석문
 * @Since 2023-11-01
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartProductWriteService {

    private final CartReadService cartReadService;
    private final CartWriteService cartWriteService;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public void createCartProduct(Long userId, CartProductCreateRequest request) {
        Cart cart = cartReadService.findCart(userId).orElse(
                cartWriteService.createCart(userId)
        );

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));

        CartProduct cartProduct = cartProductRepository.findCartProduct(cart.getId(), product.getId()).orElse(null);

        if (cartProduct != null) {
            // 상품이 이미 장바구니에 존재하는 경우
            cartProduct.updateQuantity(request.getQuantity());
        } else {
            // 상품이 장바구니에 없는 경우
            cartProduct = CartProduct
                    .builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartProductRepository.save(cartProduct);
    }

    // TODO: 현재 상품의 재고를 확인하는 코드를 만들수 없다
    public void updateQuantity(Long userId, Long cartProductId, int quantityChange) {
        Cart cart = cartReadService.findCart(userId).orElse(
                cartWriteService.createCart(userId)
        );

        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.CART_PRODUCT_NOT_FOUND));

        if (!cart.equals(cartProduct.getCart())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ResultCode.AUTH_USER_NOT);
        }

        if (!cartProduct.updateQuantity(quantityChange)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.QUANTITY_LESS_THAN_ONE);
        }

        cartProductRepository.save(cartProduct);
    }

    public void deleteCartProduct(Long userId, Long cartProductId) {
        Cart cart = cartReadService.findCart(userId).orElse(
                cartWriteService.createCart(userId)
        );

        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.CART_PRODUCT_NOT_FOUND));

        if (!cart.equals(cartProduct.getCart())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ResultCode.AUTH_USER_NOT);
        }

        cartProductRepository.delete(cartProduct);
    }
}
