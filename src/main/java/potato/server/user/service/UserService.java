package potato.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;

/**
 * @author 정순원
 * @since 2023-10-13
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
    }

    @Transactional
    public User getUserByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
    }

}
