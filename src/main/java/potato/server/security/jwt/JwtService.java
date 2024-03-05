package potato.server.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.security.auth.dto.AuthorityUserDTO;
import potato.server.security.auth.dto.ClaimsDTO;
import potato.server.user.repository.UserRepository;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 엑세스, 리프레쉬 토큰 생성, 클레임 추출, 토큰 검증
 *
 * @author 정순원
 * @since 2023-08-14
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    private static final String HEADER_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    @Value("${jwt.secretkey}")
    private String secretKey;
    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;

    //토큰 생성 메소드들
    //클레임으로 변환 후
    public String generateAccessToken(ClaimsDTO claimsDTO) {
        Claims claims = claimsByClaimsDTO(claimsDTO);
        return buildToken(claims, accessTokenExpiration);
    }


    public String generateRefreshToken(ClaimsDTO claimsDTO) {
        Claims claims = claimsByClaimsDTO(claimsDTO);
        return buildToken(claims, refreshTokenExpiration);
    }

    private Claims claimsByClaimsDTO(ClaimsDTO claimsDTO) {
        Claims claims = Jwts.claims();
        claims.setSubject(claimsDTO.getProviderId());
        claims.put("providerId", claimsDTO.getProviderId());
        claims.put("id", claimsDTO.getId());
        claims.put("email", claimsDTO.getEmail());
        claims.put("authority", claimsDTO.getAuthority());
        return claims;
    }

    private String buildToken(Claims claims, Long expiration) {
        return Jwts.
                builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰에서 클레임추출 메소드들
    public String parseProviderId(String token) {
        return (String) parseAllClaims(token).get("providerId");
    }

    public Date parseExpiration(String token) {
        return parseAllClaims(token).getExpiration();
    }

    public Claims parseAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        String providerId = parseProviderId(token);
        Date expiration = parseExpiration(token);
        if (expiration.before(new Date())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.JWT_DATE_NOT);
        }
        if (!userRepository.existsByProviderId(providerId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND);
        }
        return true;
    }


    //request 헤더에서 토큰 추출
    public String parseTokenFrom(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(HEADER_PREFIX))
            return authorizationHeader.replace(HEADER_PREFIX, "");
        return null;
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseAllClaims(accessToken);
        String providerId = claims.getSubject();
        Long id = Long.parseLong((claims.get("id")).toString());
        String email = claims.get("email").toString();
        String authority = claims.get("authority").toString();
        AuthorityUserDTO authorityMemberDTO = AuthorityUserDTO.builder()
                .id(id)
                .providerId(providerId)
                .email(email)
                .authority(authority)
                .build();
        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(authority));
        return new UsernamePasswordAuthenticationToken(authorityMemberDTO, "", authorities);
    }
}
