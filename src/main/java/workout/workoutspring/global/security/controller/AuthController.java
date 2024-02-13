package workout.workoutspring.global.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import workout.workoutspring.global.response.ApiResponse;
import workout.workoutspring.global.security.util.JwtUtil;
import workout.workoutspring.global.security.domain.dto.AccessTokenDTO;
import workout.workoutspring.global.security.domain.dto.JwtDto;
import workout.workoutspring.global.security.domain.dto.LoginRequestDTO;
import workout.workoutspring.global.security.service.LoginService;
import workout.workoutspring.global.security.service.ReIssueService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final LoginService loginService;
    private final ReIssueService reIssueService;
    private final JwtUtil jwtUtil;
    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/login")
    public ApiResponse<JwtDto> login(@RequestBody LoginRequestDTO dto) {
        JwtDto jwtDto = loginService.loginProcess(dto);

        //refreshToken은 DB에 저장
        reIssueService.save(jwtDto.getRefreshToken());

        return ApiResponse.onSuccess(jwtDto);
    }

    //refresh토큰으로 accesstoken 재발급
    @GetMapping("/reissue")
    public ApiResponse<AccessTokenDTO> reissue(HttpServletRequest request, HttpServletResponse response) {
        return ApiResponse.onSuccess(
                reIssueService.reIssueAccessToken(request, response));
    }
}
