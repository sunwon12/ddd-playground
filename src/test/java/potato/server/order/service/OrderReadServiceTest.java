package potato.server.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static potato.server.fixture.OrderFixture.createOrder;

@DisplayName("주문 서비스 테스트 - 읽기")
@ExtendWith(MockitoExtension.class)
class OrderReadServiceTest {

    @InjectMocks
    OrderReadService sut;

    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderProductReadService orderProductReadService;


    @DisplayName("주문 번호가 주어지면 응답을 반환한다.")
    @Test
    void givenOrderId_whenFindOrder_thenReturnOrderResponse() throws Exception {
        //given
        Long orderId = 1L;
        Long userId = 1L;

        given(orderRepository.findById(1L)).willReturn(Optional.ofNullable(createOrder(orderId, userId)));
        given(orderProductReadService.findOrderProductList(orderId)).willReturn(List.of());

        //when
        sut.findOrder(userId, orderId);

        //then
        then(orderRepository).should().findById(orderId);
        then(orderProductReadService).should().findOrderProductList(orderId);
    }


}
