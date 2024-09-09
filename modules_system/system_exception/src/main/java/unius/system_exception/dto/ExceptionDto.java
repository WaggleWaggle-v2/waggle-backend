package unius.system_exception.dto;

import lombok.Getter;
import unius.system_exception.type.ExceptionType;

@Getter
public class ExceptionDto {
    private final ExceptionType exceptionType;
    private final String exceptionMessage;
    private final Integer statusCode;

    public ExceptionDto(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
        this.exceptionMessage = exceptionType.getMessage();
        this.statusCode = exceptionType.getStatusCode();
    }
}
