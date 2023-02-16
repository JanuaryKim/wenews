package my.project.wenews.news.mapper;

import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface NewsMapper {


    @Mapping(target = "newsTags", ignore = true) //매핑할 source인 Post에서 newsTags는 제외 시킴
    News newsDtoPostToNews(NewsDto.Post post);


    //매핑할 로직을 직접 입력해야 하므로 default로 정의
    default News newsTagArrToNewsTagStr(News news, NewsDto.Post post) {
        String[] newsTags = post.getNewsTags();
        Arrays.sort(newsTags);

        if(newsTags.length == 0)
            return news;

        StringBuilder sb = new StringBuilder();

        sb.append("|");

        for (String tag : newsTags) {
            sb.append(tag);
            sb.append("|");
        }

        news.setNewsTags(sb.toString());
        return news;
    }

}
