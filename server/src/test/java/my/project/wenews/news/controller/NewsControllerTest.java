//package my.project.wenews.news.controller;
//
//
//import com.google.gson.Gson;
//import my.project.wenews.integration.NewsIntegrationTest;
//import my.project.wenews.member.dto.SessionUser;
//import my.project.wenews.member.entity.Member;
//import my.project.wenews.member.role.Role;
//import my.project.wenews.news.dto.NewsDto;
//import my.project.wenews.news.entity.News;
//import my.project.wenews.news.mapper.NewsMapper;
//import my.project.wenews.news.service.NewsImageService;
//import my.project.wenews.news.service.NewsService;
//import my.project.wenews.news.stub.NewsDtoStub;
//import my.project.wenews.security.auth.LoginUser;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.MethodParameter;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.multipart.MultipartFile;
//import java.nio.charset.StandardCharsets;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@WebMvcTest
//@AutoConfigureMockMvc(addFilters = false) //security 관련 사항을 테스트에서 제외시키기 위해.. build.gradle에 oauth와 spring security가 추가되어 있는데, api 레벨의 빈들만 등록되어서 내가 설정한 SecurityFilterChain 빈이 등록되지 못해서 무조건 권한을 요청함
//public class NewsControllerTest {
//
//
//    private MockMvc mvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private NewsService newsService;
//
//    @MockBean
//    private NewsImageService newsImageService;
//
//    @MockBean
//    private NewsMapper newsMapper;
//
//    @Autowired
//    private NewsController newsController;
//
//    static private Long memberId = 1L;
//    static String memberEmail = "tester@gmail.com";
//    static Role memberRole = Role.USER;
//    static String memberPicture = "testerPicture";
//    static String memberName = "JanuaryMan";
//
//    @BeforeAll
//    void beforeAll() {
//        mvc = MockMvcBuilders
//                .standaloneSetup(newsController)
//                .setCustomArgumentResolvers(new MockLoginUserArgumentResolver())
//                .build();
//    }
//
//    @Test
//    public void testCreateNews() throws Exception {
//
//        //given
//        NewsDto.Post newsPost = (NewsDto.Post) NewsDtoStub.newsMap.get("POST");
//        String content = gson.toJson(newsPost);
//
//        Long newsId = 1L;
//        String memberEmail = "tester@gmail.com";
//
//        NewsDto.Response response = new NewsDto.Response();
//        response.setNewsId(newsId);
//        response.setMemberEmail(memberEmail);
//        response.setNewsTitle(newsPost.getNewsTitle());
//        response.setNewsContents(newsPost.getNewsContents());
//        response.setNewsTags(newsPost.getNewsTags());
//
//
//        given(newsMapper.newsDtoPostToNews(any(NewsDto.Post.class), any(Member.class))).willReturn(new News());
//        given(newsMapper.newsTagArrToNewsTagStr(any(News.class), any(NewsDto.Post.class))).willReturn(new News());
//        given(newsService.createNews(any(News.class),any(MultipartFile.class))).willReturn(new News());
//        given(newsMapper.newsToNewsDtoResponse(any(News.class))).willReturn(new NewsDto.Response());
//        given(newsMapper.newsTagStrToNewsTagArr(any(NewsDto.Response.class), any(News.class))).willReturn(response);
//
//
//        //when
//        MockMultipartFile mockMultipartFile =
//                new MockMultipartFile(
//                        "news-dto",
//                        "news-dto",
//                        "application/json",content.getBytes(StandardCharsets.UTF_8));
//
//        //then
//        ResultActions perform = mvc.perform(multipart("/api/auth/news").file(mockMultipartFile));
//
//        System.out.println(perform.andReturn().getResponse().getContentAsString());
//
//        perform.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.newsId").value(newsId.toString()))
//                .andExpect(jsonPath("$.data.newsTitle").value(newsPost.getNewsTitle()))
//                .andExpect(jsonPath("$.data.newsContents").value(newsPost.getNewsContents()))
//                .andExpect(jsonPath("$.data.newsTags.[0]").value(newsPost.getNewsTags()[0]))
//                .andExpect(jsonPath("$.data.newsTags.[1]").value(newsPost.getNewsTags()[1]));
//
//    }
//
//    public static class MockLoginUserArgumentResolver implements HandlerMethodArgumentResolver { //리졸버 - 핸들러메소드(컨트롤러의 메소드)의 파라미터와 객체(데이터)를 바인딩 시켜주는 클래스
//
//        //본 리졸버가
//        @Override
//        public boolean supportsParameter(MethodParameter parameter) { //캐시 기능이 가능. 한번 요청 받은 핸들러 메소드를 본 메소드를 거치지 않음
//
//            boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
//            boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
//
//            return isLoginUserAnnotation && isUserClass;
//        }
//
//        @Override
//        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//            Member member = Member.builder()
//                    .memberId(NewsControllerTest.memberId)
//                    .memberPicture(NewsControllerTest.memberPicture)
//                    .memberName(NewsControllerTest.memberName)
//                    .memberEmail(NewsControllerTest.memberEmail)
//                    .build();
//
//            return new SessionUser(member);
//        }
//    }
//}
