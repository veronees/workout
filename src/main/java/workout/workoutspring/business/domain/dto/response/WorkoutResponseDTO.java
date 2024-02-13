package workout.workoutspring.business.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class WorkoutResponseDTO {

    private String workoutName;
    private String imageUrl;
}
