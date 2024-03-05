package potato.server.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.community.domain.Comment;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CommentPageResponse {
    private final Set<CommentResponse> commentResponses;

    public static CommentPageResponse from(List<Comment> comments) {
        Set<CommentResponse> commentResponseSet = organizeChildComments(comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toSet()));

        return new CommentPageResponse(
                commentResponseSet
        );
    }

    private static Set<CommentResponse> organizeChildComments(Set<CommentResponse> responses) {
        Map<Long, CommentResponse> map = responses.stream()
                .collect(Collectors.toMap(CommentResponse::getCommentId, Function.identity()));

        map.values().stream()
                .filter(CommentResponse::hasParentComment)
                .forEach(comment -> {
                    CommentResponse parentComment = map.get(comment.getParentCommentId());
                    parentComment.getChildComments().add(comment);
                });

        return map.values().stream()
                .filter(comment -> !comment.hasParentComment())
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator
                                .comparing(CommentResponse::getCreatedDate)
                                .reversed()
                                .thenComparing(CommentResponse::getCommentId)
                        )
                ));
    }
}
