package potato.server.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.community.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
