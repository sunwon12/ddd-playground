package potato.server.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author 허석문
 * @Since 2023-10-23
 */
@Getter
@AllArgsConstructor
public class CartProductCreateRequest {
    @NotNull
    private Long productId;
    @Positive
    private int quantity;
}
