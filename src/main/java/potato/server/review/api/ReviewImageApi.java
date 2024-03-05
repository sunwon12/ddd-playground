package potato.server.review.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.file.FileCreateRequest;
import potato.server.review.dto.request.ReviewImageDeleteRequest;
import potato.server.review.dto.response.ReviewImageCreateResponse;
import potato.server.review.service.ReiewImageService;

/**
 * @Author 정순원
 * @Since 2024-01-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review/image")
@PreAuthorize("isAuthenticated()")
public class ReviewImageApi {

    private final ReiewImageService reiewImageService;

    @PostMapping
    public ResponseForm<ReviewImageCreateResponse> createReviewImage(@RequestBody FileCreateRequest fileCreateRequest){
        return new ResponseForm<>(reiewImageService.createReviewImage(fileCreateRequest));
    }

    @DeleteMapping
    public ResponseForm deleteReviewImage(@RequestBody ReviewImageDeleteRequest reviewImageDeleteRequest ){
        reiewImageService.deleteReviewImage(reviewImageDeleteRequest);
        return new ResponseForm<>();
    }
}
