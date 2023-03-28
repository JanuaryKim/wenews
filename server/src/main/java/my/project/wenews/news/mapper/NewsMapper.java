package my.project.wenews.news.mapper;

import my.project.wenews.member.entity.Member;
import my.project.wenews.news.dto.NewsBaseDto;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.NewsImageDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {


    @Mapping(target = "newsTags", ignore = true) //매핑할 source인 Post에서 newsTags는 제외 시킴
    @Mapping(target = "member", source = "member") //member인자를 News에 member필드에 그대로 set 시킴
    News newsDtoPostToNews(NewsDto.Post post, Member member);




    //매핑할 로직을 직접 입력해야 하므로 default로 정의
    default News newsTagArrToNewsTagStr(News news, NewsBaseDto post) {
        String[] newsTags = post.getNewsTags();
        if(newsTags == null || newsTags.length == 0)
            return news;

        Arrays.sort(newsTags);

        StringBuilder sb = new StringBuilder();

        sb.append("|");

        for (String tag : newsTags) {

            if(tag.equals(""))
                continue;

            sb.append(tag);
            sb.append("|");
        }

        if(sb.length() == 1)
            news.setNewsTags(null);
        else
            news.setNewsTags(sb.toString());

        return news;
    }

    @Mapping(target = "newsTags", ignore = true) //매핑할 source인 Post에서 newsTags는 제외 시킴
    @Mapping(target = "memberEmail", source = "news.member.memberEmail")
    NewsDto.Response newsToNewsDtoResponse(News news);

    //매핑할 로직을 직접 입력해야 하므로 default로 정의
    default NewsDto.Response newsTagStrToNewsTagArr(NewsDto.Response response, News news) {

        if(news.getNewsTags() == null)
            return response;

        String tagsStr = news.getNewsTags();

        if(tagsStr.length() == 0)
            return  response;

        StringBuilder sb = new StringBuilder(tagsStr);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        String[] splTags = sb.toString().split("\\|");
        response.setNewsTags(splTags);

        return response;
    }

    default NewsDto.Response newsTagStrToNewsTagArrForUpdate(NewsDto.Response response, News news){

        NewsDto.Response tempResponse = newsTagStrToNewsTagArr(response, news);

        String[] currentTags = tempResponse.getNewsTags();

        if (currentTags.length < 3) {

            String[] newTagArr = new String[3];

            for (int i = 0; i < currentTags.length; i++) {
                newTagArr[i] = currentTags[i];
            }

            for (int i = currentTags.length; i < 3; i++) {
                newTagArr[i] = "";
            }

            tempResponse.setNewsTags(newTagArr);
        }

        return tempResponse;
    }

    @Mapping(target = "newsTags", ignore = true) //매핑할 source인 Post에서 newsTags는 제외 시킴
    @Mapping(target = "member", source = "member") //member인자를 News에 member필드에 그대로 set 시킴
    News newsDtoPutToNews(NewsDto.Put put, Member member);

    @Mapping(target = "newsImagesURL", ignore = true)
    List<NewsDto.Response> newsListToNewsDtoResponseList(List<News> newsList);

    NewsDto.SimpleResponse newsToNewsDtoSimpleResponse(News news);

}
