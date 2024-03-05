package potato.server.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.server.common.ResponseForm;
import potato.server.review.dto.response.ReviewGetResponse;
import potato.server.review.service.ReviewGetService;

import java.util.List;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
public class ReviewGetApi {

    private final ReviewGetService reviewGetService;

    @GetMapping("/all/{product-id}")
    public ResponseForm<List<ReviewGetResponse>> getAllReviewsByProduct(@PathVariable("product-id") Long productId) {
        return new ResponseForm<>(reviewGetService.getAllReviewsByProduct(productId));
    }
}
