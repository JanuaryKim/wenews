package my.project.wenews.integration;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.member.entity.Member;
import my.project.wenews.news.controller.NewsController;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.SingleResponseDto;
import my.project.wenews.security.auth.LoginUser;
import my.project.wenews.security.auth.LoginUserArgumentResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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

    MockMvc mvc;

    @Autowired
    private Gson gson;

    private Long savedNewsId;

    @Autowired
    LoginUserArgumentResolver loginUserArgumentResolver;

    @Autowired
    private NewsController newsController;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    private static Long memberId = 1L;

    @BeforeAll
    void beforeAll() {


        mvc = MockMvcBuilders
                .standaloneSetup(newsController)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .setCustomArgumentResolvers(new MockLoginUserArgumentResolver())
                .build();
    }


    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"}) //권한 통과용
    @DisplayName("뉴스 등록 성공")
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

        System.out.println(actions.andReturn().getResponse().getContentAsString());
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

        MockMultipartFile newsPutDTO = new MockMultipartFile(
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


        MockHttpServletRequestBuilder builder = multipart("/api/auth/news/{id}", 1)
                .file(newsPutDTO)
                .file(newsImage);
//                .with(oauth2Login()
//                        .authorities(new SimpleGrantedAuthority("ROLE_USER"))
//                        .attributes(a->{a.put("username","ddd");}));

        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

                request.setMethod(HttpMethod.PUT.name());
                return request;
            }
        });

        ResultActions perform = mvc.perform(builder);

        System.out.println(perform.andReturn().getResponse().getContentAsString());



    }


    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"})
    @DisplayName("뉴스 삭제 성공")
    @Test
    public void 뉴스삭제_성공() throws Exception {

        //given
        this.뉴스등록_성공();

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/api/auth/news/{id}",savedNewsId);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod(HttpMethod.DELETE.name());
                return request;
            }
        });
        //when

        ResultActions actions = mvc.perform(builder);




        //then
        actions.andExpect(status().isOk());
    }

    public static class MockLoginUserArgumentResolver implements HandlerMethodArgumentResolver { //리졸버 - 핸들러메소드(컨트롤러의 메소드)의 파라미터와 객체(데이터)를 바인딩 시켜주는 클래스

        //본 리졸버가
        @Override
        public boolean supportsParameter(MethodParameter parameter) { //캐시 기능이 가능. 한번 요청 받은 핸들러 메소드를 본 메소드를 거치지 않음

            boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
            boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

            return isLoginUserAnnotation && isUserClass;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            Member member = Member.builder()
                    .memberId(NewsIntegrationTest.memberId)
                    .memberPicture("테스트사진")
                    .memberName("테스터")
                    .memberEmail("tester@gamil.com")
                    .build();

            return new SessionUser(member);
        }
    }

}
