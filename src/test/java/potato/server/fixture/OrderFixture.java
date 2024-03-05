package potato.server.fixture;

import potato.server.order.domain.Order;
import potato.server.order.domain.OrderProduct;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static potato.server.fixture.ProductFixture.*;
import static potato.server.fixture.UserFixture.createUser;

public class OrderFixture {
    public static Order createOrder(Long orderId) throws NoSuchFieldException, IllegalAccessException {
        return createOrder(orderId, 1L);
    }

    public static Order createOrder(Long orderId, Long userId) throws NoSuchFieldException, IllegalAccessException {
        Order order = Order.builder()
                .user(createUser(userId))
                .orderPrice(BigDecimal.ONE)
                .build();

        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order, orderId);

        return order;
    }

    public static List<OrderProduct> createOrderProductList(Long orderId) throws NoSuchFieldException, IllegalAccessException {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(createOrder(orderId))
                .product(createProduct(1L))
                .build();

        Field idField = OrderProduct.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(orderProduct, 1L);

        return List.of(orderProduct);
    }



}
