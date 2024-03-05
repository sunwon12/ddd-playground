package potato.server.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.cart.dto.CartProductParam;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {
    Long cartId;
    List<CartProductParam> cartProductParamList;

    public static CartResponse of(Long cartId, List<CartProductParam> cartProductParamList) {
        return new CartResponse(cartId, cartProductParamList);
    }

}
