package my.project.wenews.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
public class ErrorResponse<T> {

    private int status;
    private String message;
    private List<T> errorReasons;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, List<T> errorReasons) {
        this.status = status;
        this.message = message;
        this.errorReasons = errorReasons;
    }


    public static ErrorResponse of(ExceptionCode exceptionCode) {

        return new ErrorResponse(exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {

        StringBuilder message = new StringBuilder();
        int httpStatus = 400;
        violations.stream().forEach(response -> message.append(response.getPropertyPath() + " : " + response.getMessageTemplate() + "\n"));
        return new ErrorResponse(httpStatus, message.toString(), ConstraintViolationErrorResponse.of(violations));
    }

    public static ErrorResponse of(BindingResult bindingResult) {

        List<FieldErrorResponse> fieldErrorResponses = FieldErrorResponse.bindingResultToFieldErrorResponseList(bindingResult);
        int httpStatus = 400;
        StringBuilder message = new StringBuilder();
        fieldErrorResponses.stream().forEach(response -> message.append(response.field + " : " + response.reason + "\n"));
        return new ErrorResponse(httpStatus, message.toString(), fieldErrorResponses);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FieldErrorResponse { //필드(변수)의 에러 정보를 담는 DTO
        private String field;
        private Object rejectedValue;
        private String reason;


        public static List<FieldErrorResponse> bindingResultToFieldErrorResponseList(BindingResult bindingResult) {
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> FieldErrorResponse.builder()
                            .field(error.getField())
                            .rejectedValue(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString())
                            .reason(error.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ConstraintViolationErrorResponse {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        public static List<ConstraintViolationErrorResponse> of(
                Set<ConstraintViolation<?>> constraintViolations) {

            List<ConstraintViolationErrorResponse> collect = constraintViolations.stream()
                    .map(constraintViolation ->
                            ConstraintViolationErrorResponse.builder()
                                    .propertyPath(constraintViolation.getPropertyPath().toString())
                                    .rejectedValue(constraintViolation.getInvalidValue().toString()) //rejectedValue 자체가 null일 수 있기때문에
                                    .reason(constraintViolation.getMessage())
                                    .build())
                    .collect(Collectors.toList());
            return collect;
        }
    }

}
