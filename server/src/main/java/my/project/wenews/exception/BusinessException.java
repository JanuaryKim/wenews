package my.project.wenews.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
