package potato.server.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.security.auth.dto.AuthenticationResponse;
import potato.server.security.auth.dto.RegisterRequest;

/**
 * 로그인, 회원가입, 토큰 재발급에 관한 API
 *
 * @Author 정순원
 * @Since 2023-08-07
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest oauth2TokenWithBearer) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, oauth2TokenWithBearer);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/login/{providerName}")
    public ResponseEntity<AuthenticationResponse> loginByOauth2Provider(@PathVariable String providerName, HttpServletRequest oauth2TokenWithBearer) {
        AuthenticationResponse authenticationResponse = authenticationService.loginByOauth2Provider(providerName, oauth2TokenWithBearer);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/login/accesstoken")
    public ResponseForm loginByAccessToken(HttpServletRequest accessTokenWithBearer) {
        authenticationService.loginByAccessToken(accessTokenWithBearer);
        return new ResponseForm<>();
    }

    @PostMapping("/renew")
    public ResponseEntity<AuthenticationResponse> newTokenByRefreshToken(HttpServletRequest refreshTokenWithBearer) {
        AuthenticationResponse authenticationResponse = authenticationService.newTokenByRefreshToken(refreshTokenWithBearer);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
