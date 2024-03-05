package potato.server.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.repository.OrderProductRepository;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;
import potato.server.review.domain.Review;
import potato.server.review.domain.ReviewImage;
import potato.server.review.dto.request.ReviewCreateRequest;
import potato.server.review.dto.response.ReviewCreateResponse;
import potato.server.review.repository.ReviewImageRepository;
import potato.server.review.repository.ReviewRepository;
import potato.server.review.spec.ChoicedExperience;
import potato.server.user.domain.User;
import potato.server.user.service.UserService;

import java.util.List;

/**
 * @author 정순원
 * @since 2023-10-13
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest request, long userId) {
        boolean hasPurchased = orderProductRepository.existsByProductAndUser(request.getProductId(), userId);
        if (!hasPurchased) {
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.ORDER_NOT_FOUND);
        }

        User user = userService.getUserById(userId);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));
        Review review = Review.builder()
                .product(product)
                .user(user)
                .content(request.getContent())
                .evaluation(request.getEvaluation())
                .choicedExperience(ChoicedExperience.valueOf(request.getChoicedExperience()))
                .build();
        reviewRepository.save(review);

        mapReviewToReviewImage(request.getReviewImageIds(), review);

        return new ReviewCreateResponse(review.getId());
    }

    private void mapReviewToReviewImage(List<Long> reviewImageIds, Review review) {
        for (Long reviewImageId : reviewImageIds) {
            ReviewImage reviewImage = reviewImageRepository.findById(reviewImageId)
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.REVIEW_IMAGE_NOT_FOUND));
            reviewImage.setReview(review);
        }
    }

    @Transactional
    public void removeReview(long userId, long reviewId) {
        isWriter(userId, reviewId);
        reviewRepository.deleteById(reviewId);
    }

    private void isWriter(long userId, long reviewId) {
        boolean tf = reviewRepository.existsByUserIdAndReviewId(userId, reviewId);

        if (!tf) {
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.REVIEW_NOT_WRITER);
        }
    }
}
