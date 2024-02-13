package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import workout.workoutspring.business.domain.entity.WorkoutRoutine;

import java.time.LocalDate;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {

    @Query("select w from WorkoutRoutine w where w.user.id = :userId")
    WorkoutRoutine findWorkoutRoutine(@Param("userId") Long userId);

    @Query("select w from WorkoutRoutine w where w.user.id = :userId and w.currentDate = :date")
    WorkoutRoutine findByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
