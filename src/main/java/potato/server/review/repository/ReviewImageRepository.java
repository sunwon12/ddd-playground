package potato.server.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
