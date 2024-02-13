package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "username", unique = true)
    private String username; //회원 가입 시 유저 ID
    private String password; //비밀번호

    @Column(name = "nickname", unique = true)
    private String nickname; //앱에서 사용할 닉네임

    @Enumerated(EnumType.STRING)
    private Gender gender; //성별 [MAN,WOMAN]

    private String location; // 사는 곳
    private int age; //나이
    private double height; //키
    private double weight; //몸무게

    //3대 1RM 무게
    private double squat;
    private double bench;
    private double deadLift;

    private String workoutCareer; //운동 경력

    @Enumerated(EnumType.STRING)
    private Role role; //회원 가입시 일반 User와 운영자 Admin 구별

    private boolean state; //탈퇴 여부 true:회원, false:탈퇴

    @OneToMany(mappedBy = "user")
    private List<WorkoutRoutine> workoutRoutines = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private File file;

    @OneToMany(mappedBy = "user")
    private List<RequestLog> requestLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Accept> accepts = new ArrayList<>();

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateLocation(String location) {
        this.location =location;
    }

    public void update1RM(double squat, double bench, double deadLift) {
        this.squat = squat;
        this.bench = bench;
        this.deadLift = deadLift;
    }

    public void updateWorkoutCareer(String workoutCareer) {
        this.workoutCareer = workoutCareer;
    }

}
