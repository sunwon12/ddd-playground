package potato.server.community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potato.server.common.ResponseForm;
import potato.server.community.dto.request.PostRequest;
import potato.server.community.dto.response.PostCreateResponse;
import potato.server.community.dto.response.PostPageResponse;
import potato.server.community.dto.response.PostResponse;
import potato.server.community.service.PostReadService;
import potato.server.community.service.PostWriteService;
import potato.server.security.auth.dto.AuthorityUserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostReadService postReadService;
    private final PostWriteService postWriteService;


    @GetMapping
    public ResponseForm<PostPageResponse> findAllPost(
            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseForm<>(postReadService.findAllPost(pageable));
    }


    @GetMapping("/{post-id}")
    public ResponseForm<PostResponse> findPost(
            @PathVariable("post-id") Long postId
    ) {
        return new ResponseForm<>(postReadService.findPost(postId));
    }

    @PostMapping
    public ResponseForm<PostCreateResponse> createPost(
            @RequestBody PostRequest request,
            @AuthenticationPrincipal AuthorityUserDTO userDTO
    ) {
        Long postId = postWriteService.createPost(request, userDTO.getId());

        return new ResponseForm<>(
                new PostCreateResponse(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(postId)
                        .toUri())
        );
    }

    @PutMapping("/{post-id}")
    public ResponseForm<Void> updatePost(
            @PathVariable("post-id") Long id,
            @RequestBody PostRequest request,
            @AuthenticationPrincipal AuthorityUserDTO userDTO
    ) {
        postWriteService.updatePost(id, request, userDTO.getId());

        return new ResponseForm<>();
    }

}
