package workout.workoutspring.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutLog {

    @Id @GeneratedValue
    @Column(name = "WORKOUTLOG_ID")
    private Long id;

    private String workoutName; //운동명(기구명)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKOUTROUTINE_ID")
    private WorkoutRoutine workoutRoutine;

    @OneToMany(mappedBy = "workoutLog")
    private List<WorkoutSet> sets = new ArrayList<>();
}
