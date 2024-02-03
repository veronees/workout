package workout.workoutspring.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WorkoutLogDTO {
    private String workoutName; //운동명(기구명)
    private List<WorkoutSetDTO> sets; //세트
}
