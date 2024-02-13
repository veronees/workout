package workout.workoutspring.business.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String title;

    private String content;

    private int viewCount;

    private int likeCount;

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "post")
    private File file;

    //메서드//
    public void updateViewCount() {
        this.viewCount += 1;
    }

    public void updateLikeCount() {
        this.likeCount += 1;
    }

    public void downLikeCount() {
        this.likeCount -= 1;
    }

    public void modifiedTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
