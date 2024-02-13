package workout.workoutspring.business.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FiveWorkoutResponse {
    private String title;
    private String chest;
    private String back;
    private String shoulder;
    private String leg;
    private String arm;
}
