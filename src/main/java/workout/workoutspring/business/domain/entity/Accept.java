package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accept extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "ACCEPT_ID")
    private Long id;

    private String acceptUsername;
    private String acceptUserNickname;

    private boolean status; //[TRUE, FALSE] == [만나서 운동 완료, 아직 안만남]

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void updateToTrue() {
        this.status = true;
    }
}
