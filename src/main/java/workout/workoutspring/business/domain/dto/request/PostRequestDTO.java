package workout.workoutspring.business.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequestDTO {
    private Long userId;

    @NotBlank(message = "제목을 적으세요.")
    private String title;

    @NotBlank(message = "내용을 적으세요")
    private String content;
}
