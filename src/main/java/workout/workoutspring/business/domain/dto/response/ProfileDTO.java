package workout.workoutspring.business.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ProfileDTO {
    private String profileImageURL; //프로필 사진

    private String username; //ID
    private String nickname; //닉네임

    private String location; // 사는 곳
    private String gender; // 성별

    private double squat; //스쿼트 1RM
    private double bench; //벤치 1RM
    private double deadLift; //데드 1RM
    private String workoutCareer; //운동 경력
}
