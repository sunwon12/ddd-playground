package potato.server.community.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.community.dto.response.CommentPageResponse;
import potato.server.community.repository.CommentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReadService {

    private final CommentRepository commentRepository;

    public CommentPageResponse findAllComment(Long postId) {
        return CommentPageResponse.from(commentRepository.findAllByPostId(postId));
    }
}
