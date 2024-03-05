package potato.server.order.dto.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Data
public class PaymentCompleteRequest {

    private String impUid;
    private Long orderId;
    private BigDecimal amount;
}
