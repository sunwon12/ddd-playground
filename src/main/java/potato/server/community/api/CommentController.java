package potato.server.community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potato.server.common.ResponseForm;
import potato.server.community.dto.request.CommentRequest;
import potato.server.community.dto.response.CommentCreateResponse;
import potato.server.community.dto.response.CommentPageResponse;
import potato.server.community.service.CommentReadService;
import potato.server.community.service.CommentWriteService;
import potato.server.security.auth.dto.AuthorityUserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{post-id}/comments")
public class CommentController {

    private final CommentReadService commentReadService;
    private final CommentWriteService commentWriteService;

    @GetMapping
    public ResponseForm<CommentPageResponse> findAllComment(
            @PathVariable("post-id") Long postId
    ) {
        return new ResponseForm<>(commentReadService.findAllComment(postId));
    }


    @PostMapping
    public ResponseForm<CommentCreateResponse> createComment(
            @PathVariable("post-id") Long postId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal AuthorityUserDTO userDto
    ) {

        Long commentId = commentWriteService.createComment(postId, request, userDto.getId());
        return new ResponseForm<>(
                new CommentCreateResponse(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(commentId)
                        .toUri())
        );
    }

    @PutMapping("/{comment-id}")
    public ResponseForm<Void> updateComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal AuthorityUserDTO userDto
    ) {
        commentWriteService.updateComment(postId, commentId, request, userDto.getId());

        return new ResponseForm<>();
    }


}
