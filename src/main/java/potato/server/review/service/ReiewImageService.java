package potato.server.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import potato.server.file.FileCreateRequest;
import potato.server.review.dto.request.ReviewImageDeleteRequest;
import potato.server.file.FileService;
import potato.server.review.domain.ReviewImage;
import potato.server.review.dto.response.ReviewImageCreateResponse;
import potato.server.review.repository.ReviewImageRepository;

@RequiredArgsConstructor
@Service
public class ReiewImageService {

    private final ReviewImageRepository reviewImageRepository;
    private final FileService fileService;

    public ReviewImageCreateResponse createReviewImage(FileCreateRequest fileCreateRequest) {
        String preSignedUrl = fileService.getPreSignedUrl(fileCreateRequest.getFileName());

        ReviewImage reviewImage = ReviewImage.builder().link(preSignedUrl).build();
        reviewImageRepository.save(reviewImage);

        return new ReviewImageCreateResponse(preSignedUrl, reviewImage.getId());
    }

    public void deleteReviewImage(ReviewImageDeleteRequest reviewImageDeleteRequest) {
        fileService.deleteFile(reviewImageDeleteRequest.getFilePath());

        reviewImageRepository.deleteById(reviewImageDeleteRequest.getReviewImageId());
    }
}
