package unius.system_exception.exception;

import lombok.Getter;
import lombok.Setter;
import unius.system_exception.type.ExceptionType;

@Getter
@Setter
public class WaggleException extends RuntimeException {
    private ExceptionType exceptionType;
    private String exceptionMessage;
    private Integer statusCode;

    public WaggleException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionType.getMessage();
        this.statusCode = exceptionType.getStatusCode();
    }
}
