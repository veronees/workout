package workout.workoutspring.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import workout.workoutspring.business.domain.dto.request.AcceptRequestDTO;
import workout.workoutspring.business.domain.dto.response.AcceptResponseDTO;
import workout.workoutspring.business.service.AcceptService;
import workout.workoutspring.global.response.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accept")
public class AcceptController {

    private final AcceptService acceptService;

    @PostMapping
    public ApiResponse<String> Accept(@RequestBody AcceptRequestDTO acceptRequestDTO, Authentication authentication) {
        String username = authentication.getName(); //요청을 받은 사람 username
        acceptService.accept(acceptRequestDTO, username);
        return ApiResponse.onSuccess("요청을 수락했습니다.");
    }

    @GetMapping("/my")
    public ApiResponse<List<AcceptResponseDTO>> showMyAccept(Authentication authentication) {
        String username = authentication.getName();
        return ApiResponse.onSuccess(acceptService.findAccept(username));
    }

    @PostMapping("/complete")
    public ApiResponse<String> completeAccept(@RequestBody AcceptRequestDTO acceptRequestDTO, Authentication authentication) {
        String username = authentication.getName();
        acceptService.completeAccept(username, acceptRequestDTO);
        return ApiResponse.onSuccess("만나서 운동을 성사했습니다.");
    }
}
