package potato.server.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.product.repository.ProductRepository;
import potato.server.review.domain.Review;
import potato.server.review.dto.response.ReviewGetResponse;
import potato.server.review.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@RequiredArgsConstructor
@Service
public class ReviewGetService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public List<ReviewGetResponse> getAllReviewsByProduct(Long productId) {
        if (!productRepository.existsById(productId))
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND);
        List<Review> reviews = reviewRepository.findByProductId(productId);

        return reviews.stream()
                .map(review -> new ReviewGetResponse(review, review.getUser()))
                .collect(Collectors.toList());
    }
}
