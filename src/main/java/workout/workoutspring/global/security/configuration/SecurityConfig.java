package workout.workoutspring.global.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import workout.workoutspring.global.security.util.JwtUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;
    private final JwtUtil jwtUtil;
    private final LogoutHandler logoutService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic((auth) -> auth //rest api이므로 기본설정 미사용
                        .disable());
        http
                .csrf((auth) -> auth //
                        .disable());
        http
                .formLogin((auth) -> auth
                        .disable());
        http
                .sessionManagement((auth) -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //jwt로 인증하므로 세션 미사용
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/admintest").hasRole("ADMIN") //어드민 기능 되는지 확인용
                        .requestMatchers("/join", "/login","/swagger-ui/**", "/**").permitAll()
                        .anyRequest().authenticated()
                );
        http
                .logout(logoutConfig -> { logoutConfig
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutService)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));

                });
        http
                .addFilterBefore(new JwtFilter(secretKey, jwtUtil), UsernamePasswordAuthenticationFilter.class); //jwt 필터



        return http.build();
    }
}