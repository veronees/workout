package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import workout.workoutspring.business.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("select p from Post p where p.id = :postId")
    Post findPostById(Long postId);
}
