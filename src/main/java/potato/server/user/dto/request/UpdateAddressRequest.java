package potato.server.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import potato.server.user.spec.DefaultAddr;

@Data
@NoArgsConstructor
public class UpdateAddressRequest {

    @NotNull
    private Long addressId;
    private String recipient;
    private String addZipcode;
    private String addr;
    private String addrDetail;
    private String addrName;
    private String userPhone;
    private String addrRequest;
    private DefaultAddr defaultAddr;
}
