package workout.workoutspring.business.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class PostThumbNailDTO {
    private String nickname;
    private String title;
    private String imagePath;
    private int viewCount;
    private int likeCount;
}
