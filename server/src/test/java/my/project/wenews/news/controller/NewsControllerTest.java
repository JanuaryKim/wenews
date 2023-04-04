package my.project.wenews.news.controller;


import com.google.gson.Gson;
import my.project.wenews.member.entity.Member;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.mapper.NewsMapper;
import my.project.wenews.news.service.NewsService;
import my.project.wenews.news.stub.NewsDtoStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest
@AutoConfigureMockMvc(addFilters = false) //security 관련 사항을 테스트에서 제외시키기 위해.. build.gradle에 oauth와 spring security가 추가되어 있는데, api 레벨의 빈들만 등록되어서 내가 설정한 SecurityFilterChain 빈이 등록되지 못해서 무조건 권한을 요청함
public class NewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Gson gson;

    @MockBean
    private NewsService newsService;

    @MockBean
    private NewsMapper newsMapper;

    @Test
    public void testCreateNews() throws Exception {

        //given
        NewsDto.Post newsPost = (NewsDto.Post) NewsDtoStub.newsMap.get("POST");
        String content = gson.toJson(newsPost);

        NewsDto.Response response = new NewsDto.Response();
        response.setNewsId(1L);
        response.setMemberEmail("테스터");
        response.setNewsTitle("테스트 제목");
        response.setNewsContents("테스트 내용");
        response.setNewsTags(new String[]{"C#","Java"});


        given(newsMapper.newsDtoPostToNews(any(NewsDto.Post.class), any(Member.class))).willReturn(new News());
        given(newsMapper.newsTagArrToNewsTagStr(any(News.class), any(NewsDto.Post.class))).willReturn(new News());
//        given(newsService.createNews(any(News.class))).willReturn(new News());
        given(newsMapper.newsToNewsDtoResponse(any(News.class))).willReturn(new NewsDto.Response());
        given(newsMapper.newsTagStrToNewsTagArr(any(NewsDto.Response.class), any(News.class))).willReturn(response);


        //when
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "news-dto",
                        "news-dto",
                        "application/json",content.getBytes(StandardCharsets.UTF_8));

        //then
        mvc.perform(multipart("/api/auth/news").file(mockMultipartFile))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.newsId").value("1"))
                .andExpect(jsonPath("$.newsTitle").value("테스트 제목"))
                .andExpect(jsonPath("$.newsContents").value("테스트 내용"))
                .andExpect(jsonPath("$.newsTags.[0]").value("C#"))
                .andExpect(jsonPath("$.newsTags.[1]").value("Java"));
    }


}
