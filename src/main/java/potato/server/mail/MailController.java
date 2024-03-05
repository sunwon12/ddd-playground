package potato.server.mail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.server.common.ResponseForm;
import potato.server.mail.dto.AuthNumberResponse;
import potato.server.mail.dto.SendMailRequest;
import potato.server.security.auth.dto.AuthorityUserDTO;

/**
 * 메일 발송 관련 API
 *
 * @author 정순원
 * @since 2024-01-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
@PreAuthorize("isAuthenticated()")
public class MailController {


    private final AuthMailService authMailService;

    /**
     * 인증번호 보내기
     */
    @PostMapping
    public ResponseForm<AuthNumberResponse> sendEmail(@Valid @RequestBody SendMailRequest request, @AuthenticationPrincipal AuthorityUserDTO authorityUserDTO) {
        return new ResponseForm<>(authMailService.sendCodeEmail(request.getEmail(), authorityUserDTO.getProviderId()));
    }
}
