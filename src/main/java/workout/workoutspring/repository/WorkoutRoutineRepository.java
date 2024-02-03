package workout.workoutspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import workout.workoutspring.domain.entity.WorkoutRoutine;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {

    @Query("select w from WorkoutRoutine w where w.user.id = :userId")
    WorkoutRoutine findWorkoutRoutine(@Param("userId") Long userId);
}
