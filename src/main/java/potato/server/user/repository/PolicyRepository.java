package potato.server.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.user.domain.Policy;

/**
 * @author 정순원
 * @since 2023-08-23
 */
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
