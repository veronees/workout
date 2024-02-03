package workout.workoutspring.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutSet {

    @Id @GeneratedValue
    @Column(name = "WORKOUTSET_ID")
    private Long id;

    private double weight; //수행한 무게
    private int repetitionCount; //수행한 횟수
    private double allVolume; //총 볼륨

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKOUTLOG_ID")
    private WorkoutLog workoutLog;
}
