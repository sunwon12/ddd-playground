package potato.server.review.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@Data
@NoArgsConstructor
public class ReviewImageDeleteRequest {

    private Long reviewImageId;
    private String filePath;
}
