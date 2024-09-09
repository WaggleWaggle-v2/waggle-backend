package unius.system_exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    // 400 BAD_REQUEST
    MISMATCH_ARGUMENT("입력 인자 형식이 잘못되었습니다.", 400),
    MISMATCH_PARAMETER("입력 매개변수 형식이 잘못되었습니다.", 400),

    // 405 METHOD_NOT_ALLOWED
    DISALLOWED_METHOD("허용되지 않은 메소드입니다.", 405),

    // 409 CONFLICT
    ALREADY_EXIST_BOOKSHELF("책장이 이미 존재합니다.", 409),

    // 422 UNPROCESSABLE_ENTITY
    INVALID_USER("유효하지 않은 사용자입니다.", 422),

    // 500 INTERNAL_SERVER_ERROR
    DOMAIN_NOT_FOUND("해당 도메인을 찾을 수 없습니다.", 500);

    private final String message;
    private final Integer statusCode;
}
