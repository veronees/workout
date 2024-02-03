package workout.workoutspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.domain.entity.WorkoutLog;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
}
