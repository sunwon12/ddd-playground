package potato.server.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.order.dto.request.PaymentCancleRequest;
import potato.server.order.dto.request.PaymentCompleteRequest;
import potato.server.order.dto.request.PaymentPreValidateRequest;
import potato.server.order.service.PaymentService;
import potato.server.security.auth.dto.AuthorityUserDTO;

/**
 * @author 정순원
 * @since 24-01-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaymentApi {

    private final PaymentService paymentService;

    //사전검증
    @PostMapping("/prepare")
    public ResponseForm preValidate(@RequestBody PaymentPreValidateRequest request) {
        paymentService.prepare(request);
        return new ResponseForm<>();
    }

    //사후검증
    @PostMapping("/complete")
    public ResponseForm paymentComplete(@RequestBody PaymentCompleteRequest request, @AuthenticationPrincipal AuthorityUserDTO userDTO) {
        paymentService.paymentComplete(request, userDTO);
        return new ResponseForm<>();
    }

    @PostMapping("/cancle")
    public ResponseForm paymentCancle(@RequestBody PaymentCancleRequest request) {
        paymentService.paymentCancle(request);
        return new ResponseForm<>();
    }
}
