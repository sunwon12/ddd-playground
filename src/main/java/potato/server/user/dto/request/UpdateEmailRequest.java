package potato.server.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEmailRequest {

    @NotNull
    private int authNumber;

    @NotBlank
    private String email;
}
