package my.project.wenews.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private List<FieldErrorResponse> fieldErrors;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, List<FieldErrorResponse> fieldErrorResponseList) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrorResponseList;
    }

    public static ErrorResponse of(ExceptionCode exceptionCode) {

        return new ErrorResponse(exceptionCode.getCode(), exceptionCode.getMessage());
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

}
