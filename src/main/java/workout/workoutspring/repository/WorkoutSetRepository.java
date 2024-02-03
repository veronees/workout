package workout.workoutspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.domain.entity.WorkoutSet;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
}
