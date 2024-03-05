package potato.server.security.auth.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 스프링 GrantedAuthority 부여해줄 때 DTO
 *
 * @Author 정순원
 * @Since 2023-08-07
 */
@Getter
@Builder
public class AuthorityUserDTO {

    private final Long id;
    private final String providerId;
    private final String email;
    private final String authority;

}

