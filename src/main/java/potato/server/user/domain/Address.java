package potato.server.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import potato.server.common.BaseTimeEntity;
import potato.server.user.dto.request.UpdateAddressRequest;
import potato.server.user.spec.DefaultAddr;

/**
 * @author 정순원
 * @since 2024-01-04
 */
@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30, nullable = false)
    private String recipient;

    @Column(length = 5, nullable = false)
    private String addZipcode;

    @Column(length = 100, nullable = false)
    private String addr;

    @Column(length = 50, nullable = false)
    private String addrDetail;

    @Column(length = 50)
    private String addrName;

    @Column(length = 13, nullable = false)
    private String userPhone;

    @Column(length = 500)
    private String addrRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DefaultAddr defaultAddr;

    @Builder
    public Address(User user, String recipient, String addZipcode, String addr, String addrDetail, String addrName, String addrRequest, String userPhone, DefaultAddr defaultAddr) {
        this.user = user;
        this.recipient = recipient;
        this.addZipcode = addZipcode;
        this.addr = addr;
        this.addrDetail = addrDetail;
        this.addrName = addrName;
        this.addrRequest = addrRequest;
        this.userPhone = userPhone;
        this.defaultAddr = defaultAddr;
    }

    public void updateDefaultAddr(DefaultAddr defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public void updateAddress(UpdateAddressRequest updateAddressRequest) {
        if (updateAddressRequest.getRecipient() != null)
            this.recipient = updateAddressRequest.getRecipient();
        if (updateAddressRequest.getAddZipcode() != null)
            this.addZipcode = updateAddressRequest.getAddZipcode();
        if (updateAddressRequest.getAddr() != null)
            this.addr = updateAddressRequest.getAddr();
        if (updateAddressRequest.getAddrDetail() != null)
            this.addrDetail = updateAddressRequest.getAddrDetail();
        if (updateAddressRequest.getAddrName() != null)
            this.addrName = updateAddressRequest.getAddrName();
        if (updateAddressRequest.getAddrRequest() != null)
            this.addrRequest = updateAddressRequest.getAddrRequest();
        if (updateAddressRequest.getUserPhone() != null)
            this.userPhone = updateAddressRequest.getUserPhone();
    }
}
