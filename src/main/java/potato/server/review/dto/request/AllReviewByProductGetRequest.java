package potato.server.review.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AllReviewByProductGetRequest {

    private Long productId;
}
