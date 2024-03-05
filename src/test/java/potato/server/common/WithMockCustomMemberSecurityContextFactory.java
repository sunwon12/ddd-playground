package potato.server.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import potato.server.security.auth.dto.AuthorityUserDTO;

import java.util.Collection;
import java.util.Set;

/**
 * Security Context 에 임의의 principal 객체 주입
 *
 * @author 정순원
 * @since 2023-11-22
 */
public class WithMockCustomMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomMember annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(annotation.role()));

        AuthorityUserDTO authUser= AuthorityUserDTO.builder()
                .id(1L)
                .email("test@test.com")
                .providerId("kakao_test")
                .build();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(authUser, "", authorities));
        return context;
    }
}