package potato.server.security.oauth;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import potato.server.user.domain.User;
import potato.server.user.spec.Authority;
import potato.server.user.spec.Gender;
import potato.server.user.spec.JoinType;

import java.util.Map;

/**
 * 네이버에서 받아오는 사용자 정보 담는 DTO
 * @Author 정순원
 * @Since 2023-08-19
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class NaverOAuth2UserAttribute extends OAuth2UserAttribute {

    private static final String NAVER_PROVIDER_ID = "naver";

    private Map<String, Object> response;


    @Override
    public User toEntity() {
        return User.builder()
                .providerName(NAVER_PROVIDER_ID)
                .providerId(getProviderId())
                .email(getEmail())
                .name(getName())
                .gender(Gender.valueOf(getGender().toUpperCase())) //대소문자 구별하니 바꿔줘야 함
                .authority(Authority.USER)
                .birth(getBirthday())
                .joinType(JoinType.NAVER)
                .build();
    }

    @Override
    public String getProviderId() {
        return NAVER_PROVIDER_ID + "_" + response.get("id").toString();
    }

    @Override
    public String getName() {
        return response.get("name").toString();
    }

    @Override
    public String getGender() {
        return response.get("gender").toString();
    }

    @Override
    public String getBirthday() {
        return response.get("birthyear").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }

    @Override
    public void setUserAttributesByOauthToken(String oauth2AccessToken) {


        JSONObject naverResponse = WebClient.create()
                .get()
                .uri("https://openapi.naver.com/v1/nid/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(oauth2AccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        this.response = (Map<String, Object>) naverResponse.get("response");

    }
}
