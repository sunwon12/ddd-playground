package potato.server.security.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.redis.RedisUtil;
import potato.server.security.auth.dto.AuthenticationResponse;
import potato.server.security.auth.dto.ClaimsDTO;
import potato.server.security.auth.dto.RegisterRequest;
import potato.server.security.jwt.JwtService;
import potato.server.security.oauth.OAuth2UserAttribute;
import potato.server.security.oauth.OAuth2UserAttributeFactory;
import potato.server.user.domain.Policy;
import potato.server.user.domain.User;
import potato.server.user.repository.PolicyRepository;
import potato.server.user.repository.UserRepository;

/**
 * @author 정순원
 * @since 2024-01-20
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final RedisUtil redisUtil;
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    /**
     * 회원가입
     * member 저장, 수신동의 저장, 엑세스,리프레쉬토큰 생성, redis에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request, HttpServletRequest oauth2TokenWithBearer) {
        //카카오인지 네이버인지 선택
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(request.getProviderName());
        String oauth2AccessToken = jwtService.parseTokenFrom(oauth2TokenWithBearer);
        //정보 추출
        oAuth2UserAttribute.setUserAttributesByOauthToken(oauth2AccessToken);
        checkRegistration(oAuth2UserAttribute.getProviderId());
        User user = saveInformation(request, oAuth2UserAttribute);
        return generateToken(user);
    }

    /**
     * Oauth2Provider 토큰으로 로그인
     * 첫 로그인 혹은 엑세스,리프레쉬 토큰 없을 때
     */
    @Transactional
    public AuthenticationResponse loginByOauth2Provider(String providerName, HttpServletRequest oauth2TokenWithBearer) {
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(providerName);
        String oauth2AccessToken = jwtService.parseTokenFrom(oauth2TokenWithBearer);
        //정보 추출
        oAuth2UserAttribute.setUserAttributesByOauthToken(oauth2AccessToken);
        String providerId = oAuth2UserAttribute.getProviderId();
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_JOINED));
        return generateToken(user);
    }

    /**
     * 엑세스 토큰으로 로그인
     */
    @Transactional
    public void loginByAccessToken(HttpServletRequest accessTokenWithBearer) {
        String accessToken = jwtService.parseTokenFrom(accessTokenWithBearer);
        jwtService.isTokenValid(accessToken);
    }

    /**
     * 리프레쉬 토큰 재발급
     */
    @Transactional
    public AuthenticationResponse newTokenByRefreshToken(HttpServletRequest refreshTokenWithBearer) {
        String refreshToken = jwtService.parseTokenFrom(refreshTokenWithBearer);
        String providerId = jwtService.parseProviderId(refreshToken);
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
        jwtService.isTokenValid(refreshToken);
        if (redisUtil.findByKey(providerId).toString().equals(refreshToken)) {
            return generateToken(user);
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.REFRESHTOKEN_OUTDATED);
    }

    /**
     * 엑세스 토큰 리프레쉬 토큰 생성, 레디쉬에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse generateToken(User user) {
        ClaimsDTO claimsDTO = ClaimsDTO.from(user);
        String providerId = claimsDTO.getProviderId();
        String accessToken = jwtService.generateAccessToken(claimsDTO);
        String refreshToken = jwtService.generateRefreshToken(claimsDTO);
        redisUtil.save(providerId, refreshToken, refreshTokenExpiration);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiration(jwtService.parseExpiration(accessToken))
                .refreshTokenExpiration(jwtService.parseExpiration(refreshToken))
                .build();
    }


    /**
     * 회원가입 되어있는지 확인
     */
    private void checkRegistration(String providerId) {
        if (userRepository.existsByProviderId(providerId))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.USER_ALREADY_JOIN);
    }

    /**
     * 유저 정보, 정책 동의 정보 저장
     */
    private User saveInformation(RegisterRequest registerRequest, OAuth2UserAttribute oAuth2UserAttribute) {
        User user = oAuth2UserAttribute.toEntity();
        Policy policy = Policy.builder().
                user(user).
                registerRequest(registerRequest).
                build();
        userRepository.save(user);
        policyRepository.save(policy);
        return user;
    }


}
