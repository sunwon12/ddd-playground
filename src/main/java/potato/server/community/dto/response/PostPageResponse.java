package potato.server.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import potato.server.community.domain.Post;

@Getter
@AllArgsConstructor
public class PostPageResponse {
    private final Page<PostResponse> postResponses;
    private final long totalElements;
    private final int totalPages;

    public static PostPageResponse from(Page<Post> postPage) {
        return new PostPageResponse(
                postPage.map(PostResponse::from),
                postPage.getTotalElements(),
                postPage.getTotalPages()
        );
    }
}
