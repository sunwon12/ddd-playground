package potato.server.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.community.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postIde);

}
