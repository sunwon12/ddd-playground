package potato.server.user.profile;

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
import potato.server.redis.RedisUtil;
import potato.server.user.domain.User;
import potato.server.user.dto.request.UpdateEmailRequest;
import potato.server.user.dto.request.UpdatePhoneNumberRequest;
import potato.server.user.dto.request.UpdateProfileImageRequest;
import potato.server.user.repository.UserRepository;
import potato.server.user.spec.Gender;
import potato.server.user.spec.JoinType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfileApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisUtil redisUtil;
    User user;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("sun won")
                .email("test@test.com")
                .providerId("kakao_test")
                .joinType(JoinType.KAKAO)
                .gender(Gender.MALE)
                .providerName("kakao")
                .phoneNumber("123-1234-1234")
                .profileImage("imageURL")
                .build();

        userRepository.save(user);
    }

    @DisplayName("프로필 조회")
    @Test
    @Transactional
    @WithMockCustomMember
    void test1() throws Exception {
        mockMvc.perform(get("/api/v1/user/profile"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("프로필 이미지 수정")
    @Test
    @Transactional
    @WithMockCustomMember
    void test2() throws Exception {

        UpdateProfileImageRequest updateProfileImageRequest = new UpdateProfileImageRequest();
        updateProfileImageRequest.setFileName("imageFileName");
        String jsonRequest = objectMapper.writeValueAsString(updateProfileImageRequest);

        mockMvc.perform(put("/api/v1/user/profile/profile-image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("이메일 수정")
    @Test
    @Transactional
    @WithMockCustomMember
    void test3() throws Exception {

        final int authNumber = 1234;
        redisUtil.saveAuthNumber("kakao_test", String.valueOf(authNumber), 1800000L);  //인증번호 검증을 위해 레디스에 저장

        UpdateEmailRequest updateEmailRequest = new UpdateEmailRequest();
        updateEmailRequest.setEmail("jsw5913@gmail.com");
        updateEmailRequest.setAuthNumber(authNumber);

        String jsonRequest = objectMapper.writeValueAsString(updateEmailRequest);

        mockMvc.perform(put("/api/v1/user/profile/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("핸드폰 번호 수정")
    @Test
    @Transactional
    @WithMockCustomMember
    void test4() throws Exception {

        UpdatePhoneNumberRequest updatePhoneNumberRequest = new UpdatePhoneNumberRequest();
        updatePhoneNumberRequest.setPhoneNumber("010-3434-3434");
        String jsonRequest = objectMapper.writeValueAsString(updatePhoneNumberRequest);

        mockMvc.perform(put("/api/v1/user/profile/phone-number")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
