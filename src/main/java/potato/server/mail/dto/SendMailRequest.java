package potato.server.mail.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
@NoArgsConstructor
public class SendMailRequest {

    @NotBlank
    private String email;
}
