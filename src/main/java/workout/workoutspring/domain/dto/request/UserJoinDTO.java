package workout.workoutspring.domain.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import workout.workoutspring.domain.entity.Gender;
import workout.workoutspring.domain.entity.Role;


@Getter @Setter
public class UserJoinDTO {
    private String username; //회원 가입 시 유저 ID
    private String password; //비밀번호
    private String nickname; //앱에서 사용할 닉네임
    private Gender gender; //성별 [MAN,WOMAN]
    private int age; //나이
    private double height; //키
    private double weight; //몸무게
    private Role role; //회원 가입시 일반 User와 운영자 Admin 구별

}
