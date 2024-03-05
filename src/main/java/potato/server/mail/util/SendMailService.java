package potato.server.mail.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Service
@RequiredArgsConstructor
public class SendMailService {

    private final JavaMailSender mailSender;

    @Value("${email.id}")
    private String fromId;

    @Async("mailExecutor")
    public void sendEmail(String to, String subject, String content) {
        MimeMessagePreparator messagePreparator =
                mimeMessage -> {
                    //true는 멀티파트 메세지를 사용하겠다는 의미
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    helper.setFrom(fromId);
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(content, true);
                };
        mailSender.send(messagePreparator);
    }


}