package unius.system_exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, HttpStatus.FORBIDDEN),
    NOT_FOUND(404, HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED),
    CONFLICT(409, HttpStatus.METHOD_NOT_ALLOWED),
    GONE(410, HttpStatus.GONE),
    UNSUPPORTED_MEDIA_TYPE(415, HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    UNPROCESSABLE_ENTITY(422, HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(503, HttpStatus.SERVICE_UNAVAILABLE);

    private final Integer status;
    private final HttpStatus httpStatus;

    private static final Map<Integer, HttpStatus> statusMap = new HashMap<>();

    static {
        for (ExceptionStatus exceptionStatus : ExceptionStatus.values()) {
            statusMap.put(exceptionStatus.getStatus(), exceptionStatus.getHttpStatus());
        }
    }

    public static HttpStatus getStatus(Integer statusCode) {
        return statusMap.get(statusCode);
    }
}
