package potato.server.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 정순원
 * @Since 2023-08-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiration;
    private Date refreshTokenExpiration;
}
