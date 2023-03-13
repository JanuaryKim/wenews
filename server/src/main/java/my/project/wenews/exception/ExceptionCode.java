package my.project.wenews.exception;


import lombok.Getter;

@Getter
public enum ExceptionCode {

    UNAUTHORIZED_EXCEPTION(401, "인증이 되지 않았습니다. 먼저 인증을 하십시오");


    private Integer code;
    private String message;

    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
