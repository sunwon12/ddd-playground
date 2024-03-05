package potato.server.security.oauth;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

/**
 * OAuth2 구별
 * @Author 정순원
 * @Since 2023-08-19
 */
public class OAuth2UserAttributeFactory {
    private static final String KAKAO_REGISTRATION_ID = "kakao";
    private static final String NAVER_REGISTRATION_ID = "naver";

    public static OAuth2UserAttribute of(String provider) {
        if (provider.equals(KAKAO_REGISTRATION_ID)) {
            return new KakaoOAuth2UserAttribute();
        }
        if (provider.equals(NAVER_REGISTRATION_ID)) {
            return new NaverOAuth2UserAttribute();
        }
        throw new OAuth2AuthenticationException("PROVIDER_NOT_SUPPORTED: " + provider);
    }
}
