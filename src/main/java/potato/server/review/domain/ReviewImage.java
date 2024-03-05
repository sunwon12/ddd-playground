package potato.server.review.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class ReviewImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(length = 500, nullable = false)
    private String link;

    @Builder
    public ReviewImage(Review review, String link) {
        this.review = review;
        this.link = link;
    }

    public void setReview(Review review){
        this.review = review;
    }
}
