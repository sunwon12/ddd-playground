package potato.server.user.dto.response;

import lombok.Data;
import potato.server.user.domain.User;

@Data
public class UserProfileResponse {

    private String profileImage;
    private String email;
    private String phoneNumber;

    public UserProfileResponse(User user) {
        this.profileImage = user.getProfileImage();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
