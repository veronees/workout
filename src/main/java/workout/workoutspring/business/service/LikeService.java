package workout.workoutspring.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.business.domain.entity.Like;
import workout.workoutspring.business.domain.entity.Post;
import workout.workoutspring.business.domain.entity.User;
import workout.workoutspring.business.repository.LikeRepository;
import workout.workoutspring.business.repository.PostRepository;
import workout.workoutspring.business.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public String likePost(Long postId, Long userId) {
        Post post = postRepository.findPostById(postId);
        User user = userRepository.findUserById(userId);
        Optional<Like> findLike = likeRepository.findByPostIdAndUserId(post, user);

        //좋아요가 있는 상태면 좋아요 취소
        if (findLike.isPresent()) {
            Like like = findLike.get();
            likeRepository.delete(like);
            post.downLikeCount();
            return "좋아요 취소";
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount();
        likeRepository.save(like);

        return "좋아요";
    }
}
