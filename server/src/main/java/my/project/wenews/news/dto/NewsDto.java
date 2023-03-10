package my.project.wenews.news.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


public class NewsDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public static class Post extends NewsBaseDto{

        private String newsTitle;
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
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    public static class Put extends NewsBaseDto{

        private String newsTitle;
        private String newsContents;
    }
}
