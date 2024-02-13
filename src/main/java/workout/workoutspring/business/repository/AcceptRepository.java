package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import workout.workoutspring.business.domain.entity.Accept;

public interface AcceptRepository extends JpaRepository<Accept, Long> {
    @Query("select a from Accept a where a.user.id = :userId and a.acceptUsername =:username and a.status = false")
    Accept findAcceptByUserIdAndStatusIsFalse(@Param("userId") Long userId, @Param("username") String username);
}
