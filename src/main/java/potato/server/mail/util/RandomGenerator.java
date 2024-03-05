package potato.server.mail.util;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author 정순원
 * @Since 2024-01-16
 */
@Service
public class RandomGenerator {

    private static final Random random = new Random();
    public int makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        return  random.nextInt(888888) + 111111;
    }
}
