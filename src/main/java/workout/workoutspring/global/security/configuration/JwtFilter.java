package workout.workoutspring.global.security.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import workout.workoutspring.global.security.util.JwtUtil;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * UserDetailsService, UserDetails를 사용한 인증 로직
         */
        //헤더에서 토큰 꺼내기

        String token = request.getHeader("Authorization");
        System.out.println("token = " + token);

        if (token != null) {
            String jwt = token.split(" ")[1];
            String tokenType = JwtUtil.getTokenType(jwt, secretKey);
            if (request.getRequestURI().equals("/reissue")) {
                if (!JwtUtil.isExpired(jwt, secretKey) && tokenType.equals("refreshToken")) {
                    Authentication authentication = jwtUtil.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                if (!JwtUtil.isExpired(jwt, secretKey) && tokenType.equals("accessToken")) {
                    Authentication authentication = jwtUtil.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}