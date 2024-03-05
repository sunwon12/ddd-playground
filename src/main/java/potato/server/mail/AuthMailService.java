package potato.server.mail;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import potato.server.mail.dto.AuthNumberResponse;
import potato.server.mail.util.RandomGenerator;
import potato.server.mail.util.SendMailService;
import potato.server.redis.RedisUtil;


/**
 * @author 정순원
 * @since 2024-01-16
 */
@Service
@RequiredArgsConstructor
public class AuthMailService {

    private static final Long EXPIRATION = 1800000L; //한시간

    private final RandomGenerator randomGenerator;
    private final SendMailService sendMailService;
    private final RedisUtil redisUtil;

    @Transactional
    public AuthNumberResponse sendCodeEmail(String email, String key) {
            int authNumber = randomGenerator.makeRandomNumber();
            String title = "Farmely 회원 가입 인증 이메일 입니다."; // 이메일 제목
            String content =
                    "홈페이지를 방문해주셔서 감사합니다." +    //html 형식으로 작성
                            "<br><br>" +
                            "인증 번호는 " + authNumber + "입니다." +
                            "<br>" +
                            "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
            sendMailService.sendEmail(email, title, content);
            redisUtil.saveAuthNumber(key, String.valueOf(authNumber), EXPIRATION);  //인증번호 검증을 위해 레디스에 저장
            return new AuthNumberResponse(authNumber);
    }
}