package workout.workoutspring.business.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkoutNextRoutineDTO {

    private String username;

    @NotBlank(message = "오늘의 운동 다짐을 적어주세요.")
    private String title;
    private WorkoutLogDTO workoutLog;
}
