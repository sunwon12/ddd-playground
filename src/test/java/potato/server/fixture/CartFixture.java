package potato.server.fixture;

import potato.server.cart.domain.Cart;
import potato.server.cart.domain.CartProduct;

import java.lang.reflect.Field;

import static potato.server.fixture.ProductFixture.createProduct;
import static potato.server.fixture.UserFixture.*;

public class CartFixture {
    public static CartProduct creatCartProduct(Long id) throws NoSuchFieldException, IllegalAccessException {
        CartProduct cartProduct = CartProduct.builder()
                .quantity(5)
                .product(createProduct(id))
                .build();

        Field idField = CartProduct.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(cartProduct, id);

        return cartProduct;
    }

    public static Cart createCart(Long id) throws NoSuchFieldException, IllegalAccessException {
        Cart cart = Cart.builder()
                .user(createUser(1L))
                .build();

        Field idField = Cart.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(cart, id);

        return cart;
    }
}
