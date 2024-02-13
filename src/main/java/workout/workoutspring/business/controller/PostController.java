package workout.workoutspring.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import workout.workoutspring.business.domain.dto.request.PostMateRequestDTO;
import workout.workoutspring.business.domain.dto.request.PostModifiedDTO;
import workout.workoutspring.business.domain.dto.request.PostRequestDTO;
import workout.workoutspring.business.domain.dto.response.PostMateResponseDTO;
import workout.workoutspring.business.domain.dto.response.PostResponseDTO;
import workout.workoutspring.business.domain.dto.response.PostThumbNailDTO;
import workout.workoutspring.business.domain.entity.UploadFile;
import workout.workoutspring.business.service.FileUploadService;
import workout.workoutspring.business.service.PostService;
import workout.workoutspring.business.service.UserService;
import workout.workoutspring.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final FileUploadService fileUploadService;
    private final UserService userService;

    /**
     * 오운완 인증 게시판 로직들
     */

    //게시글 작성(이미지 업로드 로직 추가했음!)
    @PostMapping
    public ApiResponse<String> createPost(@RequestPart("file") MultipartFile file, @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO) {
        UploadFile uploadFile = fileUploadService.fileUpload(file);
        Long postId = postService.savePost(postRequestDTO);
        fileUploadService.saveFilePostInfo(uploadFile, postId);
        return ApiResponse.onSuccess("게시글 등록 완료");
    }

    //게시글 단건 조회
    @GetMapping("{postId}")
    public ApiResponse<PostResponseDTO> showPost(@PathVariable Long postId) {
        PostResponseDTO postResponseDTO = postService.findPost(postId);
        String path = fileUploadService.getPath(postResponseDTO.getImagePath());
        postResponseDTO.setImagePath(path);
        return ApiResponse.onSuccess(postResponseDTO);
    }

    //게시글 썸네일 한 페이지 당 10개씩
    @GetMapping("/posts")
    public ApiResponse<Page<PostThumbNailDTO>> showPostThumbNail(Pageable pageable) {
        return ApiResponse.onSuccess(
                postService.paging(pageable));
    }

    //게시글 수정
    @PutMapping("{postId}")
    public ApiResponse<String> modifiedPost(@PathVariable Long postId, @RequestBody PostModifiedDTO postModifiedDTO) {
        postService.modifiedPost(postId, postModifiedDTO);
        return ApiResponse.onSuccess("수정 완료");
    }

    //게시글 삭제
    @DeleteMapping("{postId}")
    public ApiResponse<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.onSuccess("게시글 삭제 완료");
    }

    /**
     * 일일 헬스 메이트 구인 게시판 로직
     */
    @PostMapping("/mate")
    public ApiResponse<String> createPostMate(@RequestBody PostMateRequestDTO postMateRequestDTO, Authentication authentication) {
        String name = authentication.getName();
        postService.savePostMate(postMateRequestDTO, name);
        return ApiResponse.onSuccess("헬스 메이트 구인 글 작성 완료");
    }

    //일일 메이트 글 조회
    @GetMapping("/mate/{postId}")
    public ApiResponse<PostMateResponseDTO> showMatePost(@PathVariable Long postId) {
        return ApiResponse.onSuccess(postService.findPostMate(postId));
    }
}
