package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.domain.Order;
import potato.server.order.dto.response.OrderResponse;
import potato.server.order.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderReadService {

    private final OrderRepository orderRepository;
    private final OrderProductReadService orderProductReadService;

    public OrderResponse findOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, ResultCode.ORDER_NOT_FOUND));

        if (order.getUser().getId() != userId) {
            throw new CustomException(HttpStatus.FORBIDDEN, ResultCode.USER_NOT_PERMISSION);
        }
        return OrderResponse.of(order, orderProductReadService.findOrderProductList(orderId));
    }

}
