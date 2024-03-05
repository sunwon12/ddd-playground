package potato.server.user.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import potato.server.user.domain.Address;
import potato.server.user.spec.DefaultAddr;

@Data
public class AddressResponse {

    private Long addressId;
    private String recipient;
    private String addZipcode;
    private String addr;
    private String addrDetail;
    private String addrName;
    private String userPhone;
    private String addrRequest;
    private DefaultAddr defaultAddr;

    @QueryProjection
    public AddressResponse(Long addressId, String recipient, String addZipcode, String addr, String addrDetail, String addrName, String userPhone, String addrRequest, DefaultAddr defaultAddr) {
        this.addressId = addressId;
        this.recipient = recipient;
        this.addZipcode = addZipcode;
        this.addr = addr;
        this.addrDetail = addrDetail;
        this.addrName = addrName;
        this.userPhone = userPhone;
        this.addrRequest = addrRequest;
        this.defaultAddr = defaultAddr;
    }

    public static AddressResponse of(Address address) {
        return new AddressResponse(address.getId(),
                address.getRecipient(),
                address.getAddZipcode(),
                address.getAddr(),
                address.getAddrDetail(),
                address.getAddrName(),
                address.getUserPhone(),
                address.getAddrRequest(),
                address.getDefaultAddr());
    }
}
