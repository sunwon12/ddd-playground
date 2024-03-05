package potato.server.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.community.domain.Post;
import potato.server.community.dto.request.PostRequest;
import potato.server.community.repository.PostRepository;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostWriteService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);

        return postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build()).getId();
    }

    public void updatePost(Long postId, PostRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND));

        post.updatePost(user, request.getTitle(), request.getContent());
    }

}
