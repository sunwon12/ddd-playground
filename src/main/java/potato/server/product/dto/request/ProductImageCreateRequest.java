package potato.server.product.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 정순원
 * @since 2024-01-19
 */

@Data
@NoArgsConstructor
public class ProductImageCreateRequest {

    private Long productId;
    private String fileName;
}
