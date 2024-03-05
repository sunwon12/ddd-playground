package potato.server.order.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import potato.server.common.WithMockCustomMember;
import potato.server.order.domain.Order;
import potato.server.order.dto.request.PaymentPreValidateRequest;
import potato.server.order.repository.OrderRepository;
import potato.server.user.repository.UserRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author 정순원
 * @since 2024-01-20
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentApiTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ObjectMapper objectMapper;

    Order order;

    @BeforeEach
    void init() {
        order = new Order();
        orderRepository.save(order);
    }

    @DisplayName("사전 등록")
    @Test
    @Transactional
    @WithMockCustomMember
    void test1() throws Exception {

        PaymentPreValidateRequest request = new PaymentPreValidateRequest();
        request.setOrderId(order.getId());
        request.setAmount(BigDecimal.valueOf(100));

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/payment/prepare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

//
//    @DisplayName("사후 검증")
//    @Test
//    @Transactional
//    @WithMockCustomMember
//    void test2() throws Exception {
//
//        PaymentCompleteRequest request = new PaymentCompleteRequest();
//        request.setOrderId(order.getId());
//        request.setImpUid();
//        request.setAmount(BigDecimal.valueOf(100));
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(post("/api/v1/payment/complete")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}
