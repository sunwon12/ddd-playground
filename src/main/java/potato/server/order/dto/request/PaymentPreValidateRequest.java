package potato.server.order.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 2024-01-19
 */
@Data
@NoArgsConstructor
public class PaymentPreValidateRequest {

    private Long orderId;
    private BigDecimal amount;
}
