package my.project.wenews.news.dto;

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

        @Length(min = 10, max = 300)
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

        @Length(min = 10, max = 300)
        private String newsContents;
    }
}
