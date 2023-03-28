package my.project.wenews.news.dto;


import lombok.*;

public class NewsImageDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{

        private Long imageId;
        private String url;
        private Long newsId;
    }
}
