package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.business.domain.entity.WorkoutSet;

public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
}
