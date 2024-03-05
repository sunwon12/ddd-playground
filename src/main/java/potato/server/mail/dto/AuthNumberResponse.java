package potato.server.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
@AllArgsConstructor
public class AuthNumberResponse {
    private int authNumber;
}
