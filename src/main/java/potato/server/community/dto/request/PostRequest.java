package potato.server.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
