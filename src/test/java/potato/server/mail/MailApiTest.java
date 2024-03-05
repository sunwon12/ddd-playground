package potato.server.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import potato.server.common.WithMockCustomMember;
import potato.server.mail.dto.SendMailRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 정순원
 * @since 2024-01-16
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MailApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {

    }

    @DisplayName("인증 메일 발송 테스트")
    @Test
    @Transactional
    @WithMockCustomMember
    void test1() throws Exception {

        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setEmail("uglypotato5913@gmail.com");
        String jsonRequest = objectMapper.writeValueAsString(sendMailRequest);

        mockMvc.perform(post("/api/v1/mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
