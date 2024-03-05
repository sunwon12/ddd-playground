package potato.server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import potato.server.security.jwt.JwtAuthenticationEntryPoint;
import potato.server.security.jwt.JwtAuthenticationFilter;
import potato.server.security.jwt.JwtService;


/**
 * @author 정순원
 * @since 2023-09-06
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                //요청에 대한 권한 설정
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll() //해당 리소스에 대한 모든 요청 허가
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**", "/api/v1/posts/**").permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));


        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
