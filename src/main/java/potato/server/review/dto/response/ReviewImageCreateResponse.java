package potato.server.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 정순원
 * @Since 2024-01-15
 */
@Data
@AllArgsConstructor
public class ReviewImageCreateResponse {

    private String url;
    private Long reviewImageId;
}
