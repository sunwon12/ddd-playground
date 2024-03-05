package potato.server.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.order.domain.Order;
import potato.server.order.dto.OrderProductParam;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private BigDecimal orderPrice;

    List<OrderProductParam> orderProductParams;

    public static OrderResponse of(Order order, List<OrderProductParam> orderProductParams) {
        return new OrderResponse(order.getId(), order.getOrderPrice(), orderProductParams);
    }
}
