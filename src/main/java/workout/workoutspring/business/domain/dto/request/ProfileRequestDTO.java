package workout.workoutspring.business.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileRequestDTO {
//    private String profileImageURL; //프로필 사진
    private String nickname; //닉네임
    private String location; // 사는 곳
    private double squat; //스쿼트 1RM
    private double bench; //벤치 1RM
    private double deadLift; //데드 1RM
    private String workoutCareer; //운동 경력
}
