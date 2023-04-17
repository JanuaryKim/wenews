package my.project.wenews.news.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;


import java.time.LocalDateTime;
import java.util.List;


public class NewsDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public static class Post extends NewsBaseDto{

        @Length(min = 3, max = 20)
        private String newsTitle;

        @Length(min = 10, max = 1000)
        private String newsContents;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{

        private Long newsId;
        private String memberEmail;
        private String newsTitle;
        private String newsContents;
        private String[] newsTags;
        private List<NewsImageDto.Response> newsImages;

        @JsonDeserialize(using = LocalDateDeserializer.class) //json형태로 파싱할때 사용할 클래스
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //json 형태로 파싱할때 결과물의 데이터 타입, 그리고 파싱 형태 설정
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SimpleResponse{

        private Long newsId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public static class Put extends NewsBaseDto{

        @Length(min = 3, max = 20)
        private String newsTitle;

        @Length(min = 10, max = 1000)
        private String newsContents;
    }
}
