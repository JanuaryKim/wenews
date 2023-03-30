package my.project.wenews.integration;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.SingleResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class NewsIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private Gson gson;

    private Long savedNewsId;



    @DisplayName("뉴스 등록 성공")
    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"})
    @Test
    void 뉴스등록_성공() throws Exception {

        //given
        NewsDto.Post newsPost = NewsDto.Post.builder()
                .newsTitle("테스트제목")
                .newsContents("테스트내용입니다10자")
                .newsTags(new String[]{"test1","test2","test3"})
                .build();

        String content = gson.toJson(newsPost);

        MockMultipartFile newsPostDTO = new MockMultipartFile(
                "news-dto",
                "news-dto",
                "application/json",
                content.getBytes(StandardCharsets.UTF_8)
        );


        FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/wot00/OneDrive/사진/natsu2.jpeg"));

        MockMultipartFile newsImage = new MockMultipartFile(
                "news-images",
                "news-images",
                "multipart/form-data",
                fileInputStream);

        //when
        //then
        ResultActions actions = mvc.perform(multipart("/api/auth/news")
                .file(newsPostDTO)
                .file(newsImage));


        actions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("201"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.newsId").isNumber());

        SingleResponseDto<NewsDto.SimpleResponse> response = gson.fromJson(actions.andReturn().getResponse().getContentAsString(), new TypeToken<SingleResponseDto<NewsDto.SimpleResponse>>(){}.getType());

        this.savedNewsId = response.getData().getNewsId();
    }

    @DisplayName("뉴스 등록 실패 - 인가 X")
    @Test
    public void 뉴스등록_실패_인가X() throws Exception {

        //given
        NewsDto.Post newsPost = NewsDto.Post.builder()
                .newsTitle("테스트제목")
                .newsContents("테스트내용입니다10자")
                .newsTags(new String[]{"test","test2","test3"})
                .build();

        String content = gson.toJson(newsPost);

        MockMultipartFile newsPostDTO = new MockMultipartFile(
                "news-dto",
                "news-dto",
                "application/json",
                content.getBytes(StandardCharsets.UTF_8)
        );


        FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/wot00/OneDrive/사진/natsu2.jpeg"));

        MockMultipartFile newsImage = new MockMultipartFile(
                "news-images",
                "news-images",
                "multipart/form-data",
                fileInputStream);

        //when
        //then
        ResultActions actions = mvc.perform(multipart("/api/auth/news")
                .file(newsPostDTO)
                .file(newsImage));

        actions.andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("401"));
    }


    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"})
    @DisplayName("뉴스 수정 성공")
    @Test
    public void 뉴스수정_성공() throws Exception {

        this.뉴스등록_성공();

        //given
        NewsDto.Put newsPut = NewsDto.Put.builder()
                .newsTitle("수정제목")
                .newsContents("수정된내용입니다10자")
                .newsTags(new String[]{"modify1","modify2","modify3"})
                .build();

        String content = gson.toJson(newsPut);

        MockMultipartFile newsPostDTO = new MockMultipartFile(
                "news-dto",
                "news-dto",
                "application/json",
                content.getBytes(StandardCharsets.UTF_8)
        );


        FileInputStream fileInputStream = new FileInputStream(new File("C:/Users/wot00/OneDrive/사진/natsu2.jpeg"));

        MockMultipartFile newsImage = new MockMultipartFile(
                "news-images",
                "news-images",
                "multipart/form-data",
                fileInputStream);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/auth/news/{id}",savedNewsId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        //when
        //then

        ResultActions actions = mvc.perform(builder
                .file(newsPostDTO)
                .file(newsImage));


//        ResultActions actions = mvc.perform(multipart("/api/auth/news/{id}",savedNewsId)
//                .file(newsPostDTO)
//                .file(newsImage));

        System.out.println(actions.andReturn().getResponse().getContentAsString());

        actions.andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("401"));



    }



}
