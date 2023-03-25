package my.project.wenews.exception;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerExceptionAdvice {

//    @ExceptionHandler(MultipartException.class)
//    @ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
//    public ErrorResponse handleFileSizeLimitExceededException(MultipartException e) {
//
//        ErrorResponse response = ErrorResponse.of(ExceptionCode.FILESIZE_EXCEPTION);
//
//        return response;
//    }


    //클라이언트에게 실질적인 데이터를 리턴해주진 않음.
    //exception을 캐치하는 역할만 수행
    @ExceptionHandler
    public ErrorResponse handleMethodASizeLimitExceededException(SizeLimitExceededException e) {
        final ErrorResponse response = ErrorResponse.
                of(ExceptionCode.FILESIZE_EXCEPTION);

        return response;
    }

}
