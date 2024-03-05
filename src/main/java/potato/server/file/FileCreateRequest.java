package potato.server.file;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 정순원
 * @since 2024-01-15
 */
@Data
@NoArgsConstructor
public class FileCreateRequest {

    private String fileName;
}
