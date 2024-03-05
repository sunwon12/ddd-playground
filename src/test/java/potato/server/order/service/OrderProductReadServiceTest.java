package potato.server.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.fixture.OrderFixture;
import potato.server.order.repository.OrderProductRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("주문 상품 테스트 - 읽기")
@ExtendWith(MockitoExtension.class)
class OrderProductReadServiceTest {

    @InjectMocks
    OrderProductReadService sut;

    @Mock
    OrderProductRepository orderProductRepository;

    @DisplayName("주문 번호가 주어지면 주문 상품 리스트를 반환한다.")
    @Test
    public void givenOrderId_whenRequestingOrderProductList_thenReturnOrderProductList() throws Exception{
        //given
        Long orderId = 1L;
        given(orderProductRepository.findOrderProductListByOrderId(orderId)).willReturn(OrderFixture.createOrderProductList(orderId));

        //when
        sut.findOrderProductList(orderId);

        //then
        then(orderProductRepository).should().findOrderProductListByOrderId(orderId);
    }





}
