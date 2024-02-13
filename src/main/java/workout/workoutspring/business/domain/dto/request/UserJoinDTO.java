package workout.workoutspring.business.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import workout.workoutspring.business.domain.entity.Gender;
import workout.workoutspring.business.domain.entity.Role;

@Getter @Setter
public class UserJoinDTO {

    @NotBlank(message = "ID는 필수로 입력해야합니다.")
    @Size(min = 5, max = 20, message = "ID는 최소 5자 이상, 최대 20자 이하")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username; //회원 가입 시 유저 ID

    @NotBlank(message = "비밀번호를 설정하세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String password; //비밀번호

    @NotBlank(message = "닉네임을 정하세요.")
    @Size(min = 1, max = 10 ,message = "닉네임은 최대 10글자입니다.")
    private String nickname; //앱에서 사용할 닉네임

    @NotNull(message = "성별을 선택하세요.")
    private Gender gender; //성별 [MAN,WOMAN]

    @NotNull(message = "나이를 선택하세요.")
    private Integer age; //나이

    @DecimalMin(value = "0.0", inclusive = false, message = "키를 입력하세요.")
    private double height; //키

    @DecimalMin(value = "0.0", inclusive = false, message = "몸무게를 입력하세요.")
    private double weight; //몸무게

    @NotBlank(message = "일반 유저 또는 운영진 중 선택해주세요.")
    private String role; //회원 가입시 일반 User와 운영자 Admin 구별

}
