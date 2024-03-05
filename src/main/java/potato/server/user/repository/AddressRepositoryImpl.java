package potato.server.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import potato.server.user.dto.response.AddressResponse;
import potato.server.user.dto.response.QAddressResponse;
import java.util.List;

import static potato.server.user.domain.QAddress.address;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /**
     * 기본 배송지, 최신순 대로 정렬
     */
    public List<AddressResponse> findAllByUserId(Long userId) {
        return queryFactory.
                select(new QAddressResponse(
                        address.id,
                        address.recipient,
                        address.addZipcode,
                        address.addr,
                        address.addrDetail,
                        address.addrName,
                        address.userPhone,
                        address.addrRequest,
                        address.defaultAddr
                ))
                .from(address)
                .where(address.user.id.eq(userId))
                .orderBy(address.defaultAddr.desc(), address.createdDate.asc())
                .fetch();
    }
}

