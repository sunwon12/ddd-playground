package potato.server.review.dto.response;

import lombok.Data;
import potato.server.review.domain.Review;
import potato.server.user.domain.User;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
public class ReviewGetResponse {


    private String userName;
    private String content;
    private int evaluation;
    private String choicedExperience;


    public ReviewGetResponse(Review review, User user) {
        this.userName = user.getName();
        this.content = review.getContent();
        this.evaluation = review.getEvaluation();
        this.choicedExperience = review.getChoicedExperience().getKorean();
    }
}
