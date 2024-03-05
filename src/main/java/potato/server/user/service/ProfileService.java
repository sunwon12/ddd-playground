package potato.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.file.FileService;
import potato.server.redis.RedisUtil;
import potato.server.security.auth.dto.AuthorityUserDTO;
import potato.server.user.domain.User;
import potato.server.user.dto.request.UpdateEmailRequest;
import potato.server.user.dto.request.UpdatePhoneNumberRequest;
import potato.server.user.dto.request.UpdateProfileImageRequest;
import potato.server.user.dto.response.ProfileImageUpdateResponse;
import potato.server.user.dto.response.UserProfileResponse;

/**
 * @author 정순원
 * @since 2023-10-13
 */
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;
    private final RedisUtil redisUtil;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(AuthorityUserDTO authorityUserDTO) {
        User user = userService.getUserByProviderId(authorityUserDTO.getProviderId());
        return new UserProfileResponse(user);
    }

    @Transactional
    public ProfileImageUpdateResponse updateProfileImage(UpdateProfileImageRequest request, AuthorityUserDTO authorityUserDTO) {
        String preSignedUrl = fileService.getPreSignedUrl(request.getFileName());
        User user = userService.getUserByProviderId(authorityUserDTO.getProviderId());
        user.updateProfileImage(preSignedUrl);
        return new ProfileImageUpdateResponse(preSignedUrl);
    }

    @Transactional
    public void updateEmail(UpdateEmailRequest request, AuthorityUserDTO authorityUserDTO) {
        String savedAuthNumber = String.valueOf(redisUtil.findEmailAuthNumberByKey(authorityUserDTO.getProviderId()));
        String requestAuthNumber = String.valueOf(request.getAuthNumber());
        if (!savedAuthNumber.equals(requestAuthNumber))  //레디스에 저장한 인증번호와 다르다면 에러코드 보냄
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.MAIL_AUTHNUMBER_NOT);

        User user = userService.getUserByProviderId(authorityUserDTO.getProviderId());
        user.updateEmail(request.getEmail());
    }

    //TODO 핸드폰 인증 어떻게 구현할 것인가
    @Transactional
    public void updatePhoneNumber(UpdatePhoneNumberRequest request, AuthorityUserDTO authorityUserDTO) {
        User user = userService.getUserByProviderId(authorityUserDTO.getProviderId());
        user.updatePhoneNumber(request.getPhoneNumber());
    }
}
