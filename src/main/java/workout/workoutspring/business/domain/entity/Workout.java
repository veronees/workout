package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 운동 종목에 해당 운동명과 사진 저장용(유저가 운동 일지 작성할 때 운동 목록 뽑아서 보여줄 때 필요)
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Workout {

    @Id @GeneratedValue
    @Column(name = "WORKOUT_ID")
    private Long id;

    private String workoutName; //운동명

    private String target; //[chest, back, shoulder, leg, arm]

    @OneToOne(mappedBy = "workout")
    private File file;
}
