package my.project.wenews.member.dto;


import lombok.*;


public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post{

        private String memberNickname;
        private String memberPassword;
        private String memberEmail;
        private String memberAge;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private Long memberId;
        private String memberNickname;
        private String memberEmail;
        private String memberAge;
    }
}
