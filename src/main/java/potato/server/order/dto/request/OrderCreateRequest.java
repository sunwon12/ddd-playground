package potato.server.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {

    @NotEmpty
    private List<Long> cartProductIdList;

}
