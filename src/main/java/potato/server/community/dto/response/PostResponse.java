package potato.server.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.community.domain.Post;

@Getter
@AllArgsConstructor
public class PostResponse {

    private Long userId;
    private Long postId;
    private String title;
    private String content;

    public static PostResponse from(Post entity) {
        return new PostResponse(entity.getUser().getId(), entity.getId(), entity.getTitle(), entity.getContent());
    }

}
