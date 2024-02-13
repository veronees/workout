package workout.workoutspring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import workout.workoutspring.exception.dto.ErrorCode;


@RequiredArgsConstructor
public class TokenException extends RuntimeException{

    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
