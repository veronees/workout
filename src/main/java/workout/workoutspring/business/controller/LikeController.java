package workout.workoutspring.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workout.workoutspring.business.service.LikeService;
import workout.workoutspring.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/like/{postId}/{userId}")
    public ApiResponse<String> likePostV2(@PathVariable Long postId, @PathVariable Long userId) {
        return ApiResponse.onSuccess(
                likeService.likePost(postId, userId));
    }
}
