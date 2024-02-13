package workout.workoutspring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import workout.workoutspring.exception.dto.ErrorCode;

@Getter
@RequiredArgsConstructor
public class LoginException extends RuntimeException {

    private final ErrorCode errorCode;
}
