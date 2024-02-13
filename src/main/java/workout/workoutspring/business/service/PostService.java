package workout.workoutspring.business.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.business.domain.dto.request.PostMateRequestDTO;
import workout.workoutspring.business.domain.dto.request.PostModifiedDTO;
import workout.workoutspring.business.domain.dto.request.PostRequestDTO;
import workout.workoutspring.business.domain.dto.response.PostMateResponseDTO;
import workout.workoutspring.business.domain.dto.response.PostResponseDTO;
import workout.workoutspring.business.domain.dto.response.PostThumbNailDTO;
import workout.workoutspring.business.domain.entity.File;
import workout.workoutspring.business.domain.entity.Post;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.domain.entity.Workout;
import workout.workoutspring.business.repository.FileRepository;
import workout.workoutspring.business.repository.PostRepository;
import workout.workoutspring.business.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public Long savePost(PostRequestDTO postRequestDTO) {
        User user = userRepository.findById(postRequestDTO.getUserId()).get();
        Post post = Post.builder()
                .user(user)
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .build();
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public PostResponseDTO findPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        File file = post.getFile();
        post.updateViewCount(); //조회수 1증가
        String userNickname = post.getUser().getNickname();
        PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                .nickname(userNickname)
                .title(post.getTitle())
                .content(post.getContent())
                .imagePath(file.getStoreFileName())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .build();
        return postResponseDTO;
    }

    @Transactional
    public void modifiedPost(Long postId, PostModifiedDTO postModifiedDTO) {
        Post post = postRepository.findById(postId).get();
        post.modifiedTitleAndContent(postModifiedDTO.getTitle(), postModifiedDTO.getContent());
    }

    @Transactional
    public void deletePost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            File file = post.getFile();
            fileRepository.delete(file);
        }
        postRepository.deleteById(postId);
    }

    public Page<PostThumbNailDTO> paging(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        Page<PostThumbNailDTO> pageDTO = page.map(post -> new PostThumbNailDTO(post.getUser().getNickname(), post.getTitle(), fileUploadService.getPath(post.getFile().getStoreFileName()), post.getViewCount(), post.getLikeCount()));
        return pageDTO;

    }


    //헬스 메이트 글 구인 글 작성
    @Transactional
    public void savePostMate(PostMateRequestDTO postMateRequestDTO, String name) {
        User user = userRepository.findByUsername(name);
        Post post = Post.builder()
                .title(postMateRequestDTO.getTitle())
                .content(postMateRequestDTO.getContent())
                .user(user)
                .build();
        postRepository.save(post);
    }

    public PostMateResponseDTO findPostMate(Long postId) {
        Post post = postRepository.findPostById(postId);
        User user = post.getUser();

        PostMateResponseDTO postMateResponseDTO = PostMateResponseDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(user.getUsername())
                .nickName(user.getNickname())
                .gender(user.getGender().toString())
                .location(user.getLocation())
                .squat(user.getSquat())
                .bench(user.getBench())
                .deadLift(user.getDeadLift())
                .build();

        return postMateResponseDTO;
    }
}
