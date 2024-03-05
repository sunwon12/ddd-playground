package potato.server.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.community.domain.Comment;
import potato.server.community.domain.Post;
import potato.server.community.dto.request.CommentRequest;
import potato.server.community.repository.CommentRepository;
import potato.server.community.repository.PostRepository;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createComment(Long postId, CommentRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND));

        Comment comment = commentRepository.save(Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build());

        if (request.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.COMMENT_NOT_FOUNT));
            parentComment.isCorrectPost(post);
        }

        return comment.getId();
    }

    public void updateComment(Long postId, Long commentId, CommentRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.COMMENT_NOT_FOUNT));

        comment.isCorrectPost(post);
        comment.updateComment(user, request.getContent());
    }

}
