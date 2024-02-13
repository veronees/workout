package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestLog extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "REQUESTLOG_ID")
    private Long id;

    private String requestUsername;
    private String requestUserNickname;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
