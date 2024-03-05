package potato.server.user.repository;

import potato.server.user.dto.response.AddressResponse;
import java.util.List;


public interface AddressRepositoryCustom {

    List<AddressResponse> findAllByUserId(Long userId);
}
