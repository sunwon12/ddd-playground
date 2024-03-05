package potato.server.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import potato.server.user.spec.DefaultAddr;

@Data
@NoArgsConstructor
public class CreateAddressRequest {

    @NotEmpty
    private String recipient;
    @NotEmpty
    private String addZipcode;
    @NotEmpty
    private String addr;
    @NotEmpty
    private String addrDetail;
    @NotEmpty
    private String addrName;
    @NotEmpty
    private String userPhone;
    private String addrRequest;
    private DefaultAddr defaultAddr;
}
