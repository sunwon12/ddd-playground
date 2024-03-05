package potato.server.common;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 테스트 시 인증 자동화 처리를 위한 커스텀 어노테이션 (기본 역할은 인증된 유저)
 *
 * @author 정순원
 * @since 2023-11-22
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomMemberSecurityContextFactory.class)
public @interface WithMockCustomMember {
    String role() default "ROLE_AUTHUSER";
}
