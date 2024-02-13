package workout.workoutspring.exception.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import workout.workoutspring.exception.LoginException;
import workout.workoutspring.exception.dto.ErrorCode;
import workout.workoutspring.exception.dto.ValidDTO;
import workout.workoutspring.global.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비밀번호 틀렸을 경우, ID틀렸을 경우, 회원가입할 때 중복 ID로 가입하려는 경우
     */
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse handleLoginException(LoginException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ApiResponse.onFailure(String.valueOf(errorCode.getHttpStatus().value()), errorCode.getMessage(), null);
    }

    /**
     * @Valid로 걸러진 검증
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidDTO handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        List<ApiResponse> apiResponseList = new ArrayList<>();
        for (String errorMessage : errorMessages) {
            apiResponseList.add(ApiResponse.onFailure(String.valueOf(ErrorCode.BAD_REQUEST), errorMessage, null));
        }
        ValidDTO validDTO = ValidDTO.builder()
                .validCount(apiResponseList.size())
                .apiResponses(apiResponseList)
                .build();
        return validDTO;
    }
}
