package potato.server.user.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 정순원
 * @since 2023-08-07
 */
@Getter
@AllArgsConstructor
public enum Gender {

    FEMALE("여성"),
    MALE("남성");

    private String title;

}
