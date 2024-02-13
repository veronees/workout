package workout.workoutspring.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import workout.workoutspring.business.domain.dto.response.RequestLogResponseDTO;
import workout.workoutspring.business.service.RequestLogService;
import workout.workoutspring.global.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestLogController {

    private final RequestLogService requestLogService;

    @GetMapping("/mate/request-send/{userId}") // 여기 userId는 구인 글 올린 사람의 userId임
    public ApiResponse<String> requestSend(@PathVariable Long userId, Authentication authentication) {
        String username = authentication.getName(); //username은 회원가입시 ID임
        requestLogService.requestSend(userId, username);
        return ApiResponse.onSuccess("요청 완료");
    }

    @GetMapping("/mate/request/{userId}")
    public ApiResponse<List<RequestLogResponseDTO>> showRequestList(@PathVariable Long userId) {
        List<RequestLogResponseDTO> requestLogResponseDTOS = requestLogService.findRequestList(userId);
        return ApiResponse.onSuccess(requestLogResponseDTOS);
    }

}
