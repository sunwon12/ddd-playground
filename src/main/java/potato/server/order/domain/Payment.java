package potato.server.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;

import java.math.BigDecimal;

/**
 * 외부 API 연결 시 바뀔 가능성 높음
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    //아임포트 고유거래번호
    private String impUid;

    private BigDecimal totalPrice;

    private BigDecimal discountPrice;

    private BigDecimal dliveryPrice;

    @Builder
    public Payment(BigDecimal totalPrice, BigDecimal discountPrice, BigDecimal dliveryPrice) {
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.dliveryPrice = dliveryPrice;
    }

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }
}
