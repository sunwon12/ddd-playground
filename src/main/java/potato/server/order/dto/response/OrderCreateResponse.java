package potato.server.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
public class OrderCreateResponse {

    public URI uri;
}
