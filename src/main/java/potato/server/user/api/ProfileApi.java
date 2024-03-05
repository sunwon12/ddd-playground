package potato.server.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.security.auth.dto.AuthorityUserDTO;
import potato.server.user.dto.request.UpdateEmailRequest;
import potato.server.user.dto.request.UpdatePhoneNumberRequest;
import potato.server.user.dto.request.UpdateProfileImageRequest;
import potato.server.user.dto.response.ProfileImageUpdateResponse;
import potato.server.user.dto.response.UserProfileResponse;
import potato.server.user.service.ProfileService;

/**
 * @author 정순원
 * @since 2024-01-15
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileApi {

    private final ProfileService profileService;


    @GetMapping
    public ResponseForm<UserProfileResponse> getProfile(@AuthenticationPrincipal AuthorityUserDTO authorityUserDTO){
        return new ResponseForm(profileService.getProfile(authorityUserDTO));
    }

    @PutMapping("/profile-image")
    public ResponseForm<ProfileImageUpdateResponse> updateProfileImage(@Valid @RequestBody UpdateProfileImageRequest request, @AuthenticationPrincipal AuthorityUserDTO authorityUserDTO){
        return new ResponseForm<>(profileService.updateProfileImage(request, authorityUserDTO));
    }

    @PutMapping("/email")
    public ResponseForm updateEmail(@Valid @RequestBody UpdateEmailRequest request, @AuthenticationPrincipal AuthorityUserDTO authorityUserDTO){
        profileService.updateEmail(request, authorityUserDTO);
        return new ResponseForm<>();
    }

    @PutMapping("/phone-number")
    public ResponseForm updatePhoneNumber(@Valid @RequestBody UpdatePhoneNumberRequest request, @AuthenticationPrincipal AuthorityUserDTO authorityUserDTO){
        profileService.updatePhoneNumber(request, authorityUserDTO);
        return new ResponseForm<>();
    }
}
