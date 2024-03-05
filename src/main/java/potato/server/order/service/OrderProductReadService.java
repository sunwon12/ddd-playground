package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.domain.OrderProduct;
import potato.server.order.dto.OrderProductParam;
import potato.server.order.repository.OrderProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderProductReadService {

    private final OrderProductRepository orderProductRepository;

    public List<OrderProductParam> findOrderProductList(Long orderId) {
        List<OrderProduct> orderProductList = orderProductRepository.findOrderProductListByOrderId(orderId);

        return Optional.ofNullable(orderProductList)
                .filter(list -> !list.isEmpty())
                .map(list -> list.stream()
                        .map(OrderProductParam::from)
                        .toList())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, ResultCode.ORDER_PRODUCT_NOT_FOUND));
    }

}
