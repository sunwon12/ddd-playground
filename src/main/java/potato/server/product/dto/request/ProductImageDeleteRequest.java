package potato.server.product.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
@NoArgsConstructor
public class ProductImageDeleteRequest {

    private Long productImageId;
    private String filePath;
}
