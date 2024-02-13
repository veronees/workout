package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.business.domain.entity.WorkoutLog;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
}
