package workout.workoutspring.business.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import workout.workoutspring.business.domain.dto.request.PasswordDTO;
import workout.workoutspring.business.domain.dto.request.ProfileRequestDTO;
import workout.workoutspring.business.domain.dto.request.UserJoinDTO;
import workout.workoutspring.business.domain.dto.response.ProfileDTO;
import workout.workoutspring.business.domain.entity.UploadFile;
import workout.workoutspring.business.service.FileUploadService;
import workout.workoutspring.business.service.UserService;
import workout.workoutspring.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FileUploadService fileUploadService;

    //회원 가입
    @PostMapping("/join")
    public ApiResponse<String> join(@Valid @RequestBody UserJoinDTO userJoinDTO) {
        userService.join(userJoinDTO);
        return ApiResponse.onSuccess("회원가입 완료");
    }

    //회원 탈퇴
    @PostMapping("/withdraw/{userId}")
    public ApiResponse<String> withDraw(@RequestBody PasswordDTO passwordDTO, @PathVariable Long userId) {
        userService.deleteUser(passwordDTO, userId);
        return ApiResponse.onSuccess("회원 탈퇴 완료");
    }

    //프로필 정보 업데이트
    @PostMapping("/profile/{userId}")
    public ApiResponse<String> updateMyProfile(@RequestBody ProfileRequestDTO profileRequestDTO, @PathVariable Long userId) {
        userService.updateProfile(profileRequestDTO, userId);
        return ApiResponse.onSuccess("프로필 수정 완료");
    }

    //프로필 사진 업데이트
    @PostMapping("profile/image/{userId}")
    public ApiResponse<String> updateMyProfileImage(@RequestPart("file") MultipartFile file, @PathVariable Long userId) {
        UploadFile uploadFile = fileUploadService.fileUpload(file);
        fileUploadService.saveFileProfileInfo(uploadFile, userId);
        return ApiResponse.onSuccess("프로필 사진 등록 완료");
    }

    //프로필 조회
    @GetMapping("/profile/{userId}")
    public ApiResponse<ProfileDTO> showProfile(@PathVariable Long userId) {
        return ApiResponse.onSuccess(
                userService.findUserInfo(userId));
    }
}