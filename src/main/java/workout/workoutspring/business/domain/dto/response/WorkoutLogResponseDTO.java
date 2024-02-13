package workout.workoutspring.business.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class WorkoutLogResponseDTO {
    private String workoutName; // 운동명(기구명)
    private List<WorkoutSetResponseDTO> sets;
    private double perVolume; //종목별 총 볼륨
}
