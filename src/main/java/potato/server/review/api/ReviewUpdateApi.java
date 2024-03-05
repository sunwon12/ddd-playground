package potato.server.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.review.dto.request.ReviewCreateRequest;
import potato.server.review.dto.response.ReviewCreateResponse;
import potato.server.review.service.ReviewService;
import potato.server.security.auth.dto.AuthorityUserDTO;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
@PreAuthorize("isAuthenticated()")
public class ReviewUpdateApi {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseForm<ReviewCreateResponse> createReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @RequestBody ReviewCreateRequest request) {
        return new ResponseForm<>(reviewService.createReview(request, userDTO.getId()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseForm removeReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @PathVariable long reviewId) {
        reviewService.removeReview(userDTO.getId(), reviewId);
        return new ResponseForm<>();
    }
}
