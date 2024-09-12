package unius.system_exception.handler;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import unius.system_exception.dto.ExceptionDto;
import unius.system_exception.exception.WaggleException;
import unius.system_exception.type.ExceptionStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static unius.system_exception.type.ExceptionType.*;

@RestControllerAdvice
public class WaggleExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleHttpRequestMethodNotSupportedExceptions() {
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(new ExceptionDto(DISALLOWED_METHOD));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            UnexpectedTypeException.class})
    public ResponseEntity<ExceptionDto> handleMethodArgumentExceptions() {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ExceptionDto(MISMATCH_ARGUMENT));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionDto> handleMissingServletRequestParameterExceptions() {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ExceptionDto(MISMATCH_PARAMETER));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionDto> handleMissingRequestHeaderExceptions() {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ExceptionDto(MISMATCH_HEADER));
    }

    @ExceptionHandler(WaggleException.class)
    public ResponseEntity<ExceptionDto> handleWaggleExceptions(WaggleException e) {
        return ResponseEntity
                .status(ExceptionStatus.getStatus(e.getStatusCode()))
                .body(new ExceptionDto(e.getExceptionType()));
    }
}
