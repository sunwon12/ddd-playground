package potato.server.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuantityUpdateRequest {
    int quantity;
}
