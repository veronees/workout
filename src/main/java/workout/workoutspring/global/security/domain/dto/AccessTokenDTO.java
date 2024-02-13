package workout.workoutspring.global.security.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AccessTokenDTO {
    private String accessToken;
}
