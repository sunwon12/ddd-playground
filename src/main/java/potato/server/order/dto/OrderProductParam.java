package potato.server.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.order.domain.OrderProduct;
import potato.server.product.dto.ProductParam;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderProductParam {

    private Long orderProductId;
    private int count;
    private BigDecimal price;
    private ProductParam productParam;

    public static OrderProductParam from(OrderProduct entity) {
        return new OrderProductParam(entity.getId(), entity.getCount(), entity.getPrice(), ProductParam.from(entity.getProduct()));
    }
}
