package my.project.wenews.news.mapper;


import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.stub.NewsDtoStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;


@Transactional
//@ExtendWith(MockitoExtension.class)
public class NewsMapperTest {

    private NewsMapperImpl newsMapperImpl = new NewsMapperImpl();

    @Test
    void testNewsTagArrToNewsTagStr() {
        NewsDto.Post post = (NewsDto.Post)NewsDtoStub.newsMap.get("POST");
        News news = newsMapperImpl.newsTagArrToNewsTagStr(new News(), post);
        Assertions.assertEquals( "|C#|Java|",news.getNewsTags());
    }

    @Test
    void testNewsTagStrToNewsTagArr() {
        NewsDto.Response response = new NewsDto.Response();
        News news = new News();
        news.setNewsTags("|C#|Java|");
        NewsDto.Response result = newsMapperImpl.newsTagStrToNewsTagArr(response, news);

        //두 배열의 요소들이 같은지 확인
        Assertions.assertArrayEquals(new String[] {"C#","Java"},result.getNewsTags());
    }

}
