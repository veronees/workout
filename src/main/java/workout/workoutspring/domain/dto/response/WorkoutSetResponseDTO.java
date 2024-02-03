package workout.workoutspring.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class WorkoutSetResponseDTO {
    private double weight; // 수행한 무게
    private int repetitionCount; // 수행한 횟수
}
