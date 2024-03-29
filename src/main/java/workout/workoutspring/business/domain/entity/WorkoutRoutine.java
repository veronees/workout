package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutRoutine {

    @Id @GeneratedValue
    @Column(name = "WORKOUTROUTINE_ID")
    private Long id;

    private String title; //오늘의 운동 루틴 제목 or 오늘의 다짐

    @Column(name = "log_date") //mysql에서 currentDate가 예약어라서.
    private LocalDate currentDate; //운동 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "workoutRoutine")
    private List<WorkoutLog> workoutLogs = new ArrayList<>();
}
