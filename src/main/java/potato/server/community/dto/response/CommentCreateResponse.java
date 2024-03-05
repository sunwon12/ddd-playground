package potato.server.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
public class CommentCreateResponse {

    public URI uri;
}
