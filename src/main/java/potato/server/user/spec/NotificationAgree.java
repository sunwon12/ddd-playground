package potato.server.user.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 정순원
 * @since 2023-08-23
 */
@Getter
@AllArgsConstructor
public enum NotificationAgree {

    Y("동의"),
    N("거부");

    private String status;

}
