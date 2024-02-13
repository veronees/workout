package workout.workoutspring.business.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PostMateResponseDTO {
    private String title;
    private String content;
    private String username;
    private String nickName;
    private String gender;
    private double squat;
    private double bench;
    private double deadLift;
    private String location;
}
