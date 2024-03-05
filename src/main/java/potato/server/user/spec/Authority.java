package potato.server.user.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 정순원
 * @since 2023-08-07
 */
@AllArgsConstructor
@Getter
public enum Authority {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
