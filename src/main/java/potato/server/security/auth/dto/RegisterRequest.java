package potato.server.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author 정순원
 * @Since 2023-08-23
 */
@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String providerName;
    @NotBlank
    private String serviceUsingAgree;
    @NotBlank
    private String personalInformationAgree;
    @NotBlank
    private String marketingAgree;
}
