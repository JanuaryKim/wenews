package my.project.wenews.news.stub;

import my.project.wenews.news.dto.NewsDto;
import java.util.Map;


public class NewsStub {

    public static Map<String, Object> newsMap;

    static{

        NewsDto.Post post = NewsDto.Post.builder()
                .newsTitle("테스트 제목")
                .newsContents("테스트 내용")
                .newsTags(new String[]{"Java", "C#"})
                        .build();

        newsMap.put("POST", post);
    }
}
