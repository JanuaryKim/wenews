package my.project.wenews.exception;


import lombok.Getter;

@Getter
public enum ExceptionCode {

    UNAUTHORIZED_EXCEPTION(401, "인증이 되지 않았습니다. 먼저 인증을 하십시오"),
    FILESIZE_EXCEPTION(413, "파일의 최대 업로드 크기를 초과하였습니다. (최대 : 10MB)");

    private Integer code;
    private String message;

    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
