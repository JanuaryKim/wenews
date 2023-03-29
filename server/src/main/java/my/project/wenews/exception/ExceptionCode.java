package my.project.wenews.exception;


import lombok.Getter;

@Getter
public enum ExceptionCode {

    UNAUTHORIZED_EXCEPTION(401, "인증이 되지 않았습니다. 먼저 인증을 하십시오"),
    FILESIZE_EXCEPTION(413, "파일의 최대 업로드 크기를 초과하였습니다. (최대 : 10MB)"),
    NOT_EXISTS_NEWS_IMG(400, "존재하지 않는 뉴스 이미지입니다"),
    ALREADY_EXISTS_NEWS_IMG(400, "이미 이미지가 존재합니다. 기존 이미지를 삭제하세요"),
    NOT_AUTHOR_OF_NEWS(400, "뉴스의 작성자가 아닙니다");


    private Integer code;
    private String message;

    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
