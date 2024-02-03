package workout.workoutspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findOptionalByUsername(String username);
}
