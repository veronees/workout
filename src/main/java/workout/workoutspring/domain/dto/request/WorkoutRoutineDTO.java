package workout.workoutspring.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WorkoutRoutineDTO {
    private String username; //유저아이디
    private String title; //운동 일지 제목
    private List<WorkoutLogDTO> workoutLogs;
}
