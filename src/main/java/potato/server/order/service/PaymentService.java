package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.domain.Order;
import potato.server.order.domain.Payment;
import potato.server.order.dto.request.PaymentCancleRequest;
import potato.server.order.dto.request.PaymentCompleteRequest;
import potato.server.order.dto.request.PaymentPreValidateRequest;
import potato.server.order.repository.OrderRepository;
import potato.server.order.repository.PaymentRepository;
import potato.server.security.auth.dto.AuthorityUserDTO;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ImpPaymentService impPaymentService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void prepare(PaymentPreValidateRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ORDER_NOT_FOUND));

        //사전등록
        impPaymentService.prepare(request);

        Payment payment = Payment.builder().totalPrice(request.getAmount()).build();
        order.setPayment(payment);
        paymentRepository.save(payment);
    }

    @Transactional
    public void paymentComplete(PaymentCompleteRequest request, AuthorityUserDTO userDTO) {
        //사후 검증
        BigDecimal amount = impPaymentService.paymentInfo(request.getImpUid());

        Order order = orderRepository.findOrderById(request.getOrderId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ORDER_NOT_FOUND));
        Payment payment = order.getPayment();
        //사후 검증에서 틀리면 결제 취소
        if (!payment.getTotalPrice().equals(amount)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_AMOUNT);
        }
        payment.setImpUid(request.getImpUid());
    }

    @Transactional
    public void paymentCancle(PaymentCancleRequest paymentCancleRequest) {
        //imp cancle 호출
        impPaymentService.refund(paymentCancleRequest.getOrderId(), paymentCancleRequest.getAmount(), paymentCancleRequest.getReason());
        //주문테이블에서 삭제
        //상품재고 추가
    }
}
