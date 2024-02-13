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
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private Comment parentComment; //해당 외래키 같은 테이블에 생성 이 외래키는 comment(Comment_ID)를 참조함. 그래서 순환 참조.
    //이게 null인 행은 최상위 계층인 것임.

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();




    //-----------메서드----------//

    public void modifiedContent(String content) {
        this.content = content;
//        this.lastModifiedDate = LocalDateTime.now();
    }

    public void deleteContent() {
        this.content = "삭제된 댓글입니다.";
    }
}