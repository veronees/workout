package workout.workoutspring.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import workout.workoutspring.global.response.ApiResponse;

import java.util.List;

@Getter @Setter @Builder
public class ValidDTO {
    private int validCount;
    private List<ApiResponse> apiResponses;


}
