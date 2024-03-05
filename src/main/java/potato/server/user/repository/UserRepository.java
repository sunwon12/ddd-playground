package potato.server.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.user.domain.User;

import java.util.Optional;

/**
 * @author: 정순원
 * @since: 2023-09-06
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String memberEmail);

    boolean existsById(Long Long);

    Optional<User> findByProviderId(String providerId);

    boolean existsByProviderId(String providerId);
}
