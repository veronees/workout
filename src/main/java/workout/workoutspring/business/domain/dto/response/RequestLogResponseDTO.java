package workout.workoutspring.business.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class RequestLogResponseDTO {

    private String requestUsername;
    private String requestUserNickname;
}
