package workout.workoutspring.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String username; //회원 가입 시 유저 ID
    private String password; //비밀번호
    private String nickname; //앱에서 사용할 닉네임

    @Enumerated(EnumType.STRING)
    private Gender gender; //성별 [MAN,WOMAN]

    private int age; //나이
    private double height; //키
    private double weight; //몸무게

    @Enumerated(EnumType.STRING)
    private Role role; //회원 가입시 일반 User와 운영자 Admin 구별

    private boolean state; //탈퇴 여부 true:회원, false:탈퇴

    @OneToMany(mappedBy = "user")
    private List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

}
