package potato.server.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;
import potato.server.security.auth.dto.RegisterRequest;
import potato.server.user.spec.NotificationAgree;

/**
 * 광고동의여부확인
 * @author 정순원
 * @since 2023-09-06
 */
@Entity
@Getter
@NoArgsConstructor
public class Policy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationAgree serviceUsingAgree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationAgree personalInformationAgree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationAgree marketingAgree;

    @Builder
    public Policy  (User user, RegisterRequest registerRequest) {
        this.user = user;
        this.serviceUsingAgree = NotificationAgree.valueOf(registerRequest.getServiceUsingAgree());
        this.personalInformationAgree = NotificationAgree.valueOf(registerRequest.getPersonalInformationAgree());
        this.marketingAgree = NotificationAgree.valueOf(registerRequest.getMarketingAgree());
    }
}