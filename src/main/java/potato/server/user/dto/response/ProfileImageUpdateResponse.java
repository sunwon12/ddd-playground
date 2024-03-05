package potato.server.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
@AllArgsConstructor
public class ProfileImageUpdateResponse {

    private String presignedURL;
}
