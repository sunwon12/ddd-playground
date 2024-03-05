package potato.server.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.community.domain.Comment;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long userId;
    private Long commentId;
    private Long parentCommentId;
    private String content;
    private LocalDateTime createdDate;
    private Set<CommentResponse> childComments;


    public static CommentResponse from(Comment entity) {
        Comment parentComment = entity.getParentComment();

        Set<CommentResponse> childComments = new TreeSet<>(Comparator
                .comparing(CommentResponse::getCreatedDate)
                .thenComparing(CommentResponse::getCommentId));

        return new CommentResponse(
                entity.getUser().getId(),
                entity.getId(),
                parentComment != null ? parentComment.getId() : null,
                entity.getContent(),
                entity.getCreatedDate(),
                childComments
        );
    }

    public boolean hasParentComment() {
        return getParentCommentId() != null;
    }
}
