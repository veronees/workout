package workout.workoutspring.global.security.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtDto {

    private String accessToken;
    private String refreshToken;
}
