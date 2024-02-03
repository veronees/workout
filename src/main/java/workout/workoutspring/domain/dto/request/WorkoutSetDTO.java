package workout.workoutspring.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkoutSetDTO {
    private double weight; //수행한 무게
    private int repetitionCount; //수행한 횟수
}
