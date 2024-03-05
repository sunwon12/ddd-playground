package potato.server.review.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@AllArgsConstructor
@Getter
public enum ChoicedExperience {

    GOOD_QUIRTY("퀄리티가 좋다"),
    FAST_DLIVERY("배송이 빠르다"),
    MEANINGFUL("의미가 있다"),
    NOTHING("해당 없다");

    private String korean;

}
