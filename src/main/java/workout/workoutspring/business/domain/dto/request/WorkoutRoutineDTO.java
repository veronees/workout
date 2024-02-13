package workout.workoutspring.business.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WorkoutRoutineDTO {
    private String username; //유저아이디

    @NotBlank(message = "오늘의 운동 다짐을 적어주세요.")
    private String title; //오늘의 운동 다짐
//    private LocalDate currentDate; //운동 날짜
    private List<WorkoutLogDTO> workoutLogs;
}
