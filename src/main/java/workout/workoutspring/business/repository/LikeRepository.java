package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import workout.workoutspring.business.domain.entity.Like;
import workout.workoutspring.business.domain.entity.Post;
import workout.workoutspring.business.domain.entity.User;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.post = :post and l.user = :user")
    Optional<Like> findByPostIdAndUserId(@Param("post") Post post, @Param("user") User user);
}
