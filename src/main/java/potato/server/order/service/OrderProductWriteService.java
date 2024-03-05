package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.service.CartProductWriteService;
import potato.server.order.domain.Order;
import potato.server.order.domain.OrderProduct;
import potato.server.order.repository.OrderProductRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductWriteService {

    private final OrderProductRepository orderProductRepository;
    private final CartProductWriteService cartProductWriteService;

    public void createOrderProductWithCart(Long userId, Order order, List<CartProduct> cartProductParamList) {
        cartProductParamList.forEach(c -> {
            orderProductRepository.save(
                    OrderProduct.builder()
                            .order(order)
                            .product(c.getProduct())
                            .price(c.getProduct().getPrice())
                            .build());

            cartProductWriteService.deleteCartProduct(userId, c.getId());
        });
    }

}
