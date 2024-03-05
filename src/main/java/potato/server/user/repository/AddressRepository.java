package potato.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.user.domain.Address;
import potato.server.user.domain.User;
import potato.server.user.spec.DefaultAddr;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {

    Boolean existsByUserAndDefaultAddr(User user, DefaultAddr defaultAddr);

    Optional<Address> findByUserIdAndDefaultAddr(Long userId, DefaultAddr defaultAddr);
}
