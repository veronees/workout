package workout.workoutspring.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request."),

    /*
     * 401 UNAUTHORIZED: 인증되지 않은 사용자의 요청
     */
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "Unauthorized."),

    /*
     * 403 FORBIDDEN: 권한이 없는 사용자의 요청
     */
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "Forbidden."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not allowed method."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error."),

    /**
     * 존재하지 않는 ID일 경우
     */
    NO_EXIST_USER(HttpStatus.UNAUTHORIZED, "존재하지 않는 회원입니다."),

    /**
     * 비밀번호 틀린 경우
     */
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    /**
     * 회원가입에서 이미 존재하는 ID로 회원가입을 하려는 경우
     */
    EXIST_ID(HttpStatus.BAD_REQUEST, "이미 사용 중인 ID입니다."),

    /**
     * accessToken이 없을 경우
     */
    NO_TOKEN(HttpStatus.UNAUTHORIZED, "Token과 함께 요청하십시오."),

    /**
     * refreshToken이 만료된 경우
     */
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
