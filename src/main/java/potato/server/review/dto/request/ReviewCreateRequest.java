package potato.server.review.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@Data
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private Long productId;
    private String content;
    @NotNull
    private int evaluation;
    private String choicedExperience;
    private List<Long> reviewImageIds = new ArrayList<>();
}
