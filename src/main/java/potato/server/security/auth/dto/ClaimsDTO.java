package potato.server.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import potato.server.user.domain.User;

import static lombok.AccessLevel.PRIVATE;

/**
 * 엑세스토큰 claim에 담길 정보들 넘겨주는 DTO
 *
 * @Author 정순원
 * @Since 2023-08-19
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ClaimsDTO {

    private final Long id;
    private final String providerId;
    private final String email;
    private final String authority;


    public static ClaimsDTO from(User user) {
        return ClaimsDTO.builder()
                .id(user.getId())
                .providerId(user.getProviderId())
                .email(user.getEmail())
                .authority(user.getAuthority().getRole())
                .build();
    }
}
