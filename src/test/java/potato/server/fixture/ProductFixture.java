package potato.server.fixture;

import potato.server.product.domain.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class ProductFixture {
    public static Product createProduct(Long id) throws NoSuchFieldException, IllegalAccessException {
        Product product = Product.builder()
                .price(BigDecimal.ONE)
                .build();

        Field idField = Product.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(product, id);

        return product;
    }
}
