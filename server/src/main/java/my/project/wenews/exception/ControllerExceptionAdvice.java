package my.project.wenews.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    public ErrorResponse handleBusinessException(BusinessException e) {

        ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return response;
    }

    //DTO에 담겨 오는 값의 유효성 검증
    @ExceptionHandler
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {

        ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

}
