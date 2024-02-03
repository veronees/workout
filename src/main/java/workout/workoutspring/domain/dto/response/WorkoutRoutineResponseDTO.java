package workout.workoutspring.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorkoutRoutineResponseDTO {
    private String title; // 운동 일지 제목
    private List<WorkoutLogResponseDTO> workoutLogs;
    private double allVolume; // 운동 전체 볼륨

}
