package potato.server.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Data
@NoArgsConstructor
public class UpdatePhoneNumberRequest {

    @NotBlank
    private String phoneNumber;
}
