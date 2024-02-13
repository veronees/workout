package workout.workoutspring.global.security.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.exception.TokenException;
import workout.workoutspring.exception.dto.ErrorCode;
import workout.workoutspring.global.security.domain.dto.AccessTokenDTO;
import workout.workoutspring.global.security.domain.entity.RefreshToken;
import workout.workoutspring.global.security.repository.RefreshTokenRepository;
import workout.workoutspring.global.security.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class ReIssueService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    public void save(String refreshToken) {
        RefreshToken build = RefreshToken.builder()
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .username(JwtUtil.getUsername(refreshToken, secretKey))
                .build();
        refreshTokenRepository.save(build);
    }


    //refreshtoken으로 요청해서 accesstoken 재발급 받는 로직
    @Transactional
    public AccessTokenDTO reIssueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("AUTHORIZATION");

        String refreshToken = authorization.split(" ")[1];
        String username1 = JwtUtil.getUsername(refreshToken, secretKey);
        RefreshToken findToken = refreshTokenRepository.find(username1); //여기서 찾은 결과는 엔티티임
        String findTokenToString = findToken.getRefreshToken(); //엔티티에서 최종 refreshToken 값을 꺼내줘야함
        String reIssuedToken = null;
        try {


            if (findTokenToString.equals(refreshToken)) {
                boolean expired = JwtUtil.isExpired(refreshToken, secretKey); //true면 만료, false면 만료x
                //토큰이 실제로 기간 만료가 아니고, expire 디비 값이 false이고, revoke 디비 값이 false이면 -> 아직 만료 안된 토큰임
                if (expired == false && findToken.isExpired() == false && findToken.isRevoked() == false) {
                    String username = JwtUtil.getUsername(refreshToken, secretKey);
                    reIssuedToken = JwtUtil.reIssueAccessToken(username, secretKey);
                }
            }
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        }
        AccessTokenDTO accessTokenDTO = AccessTokenDTO.builder()
                .accessToken(reIssuedToken)
                .build();

        return accessTokenDTO;
    }
}
