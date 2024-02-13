package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.business.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
