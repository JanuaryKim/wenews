package my.project.wenews.news.dto;

import lombok.*;


public class NewsDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post {

        private String newsTitle;
        private String newsContents;
        private String[] newsTags;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{

        private Long newsId;
        private String memberNickname;
        private String newsTitle;
        private String newsContents;
        private String[] newsTags;
    }
}
