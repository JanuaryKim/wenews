package my.project.wenews.news.stub;

import my.project.wenews.news.dto.NewsDto;

import java.util.HashMap;
import java.util.Map;


public class NewsDtoStub {

    public static Map<String, Object> newsMap = new HashMap<>();

    static{

        NewsDto.Post post = NewsDto.Post.builder()
                .newsTitle("테스트 제목")
                .newsContents("테스트 내용입니다. 10자 이상")
                .newsTags(new String[]{"Java", "C언어","C플러스"})
                        .build();

        newsMap.put("POST", post);
    }
}
