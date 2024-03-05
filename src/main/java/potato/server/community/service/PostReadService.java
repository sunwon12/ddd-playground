package potato.server.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.community.domain.Post;
import potato.server.community.dto.response.PostPageResponse;
import potato.server.community.dto.response.PostResponse;
import potato.server.community.repository.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;

    public PostPageResponse findAllPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return PostPageResponse.from(posts);
    }

    public PostResponse findPost(Long postId) {
        return PostResponse.from(postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND)));
    }

}
