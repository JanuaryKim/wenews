package my.project.wenews.integration;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import my.project.wenews.config.DatabaseCleaner;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.member.entity.Member;
import my.project.wenews.member.repository.MemberRepository;
import my.project.wenews.member.role.Role;
import my.project.wenews.news.controller.NewsController;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.SingleResponseDto;
import my.project.wenews.security.auth.LoginUser;
import my.project.wenews.security.auth.LoginUserArgumentResolver;
import org.junit.jupiter.api.*;
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
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class NewsIntegrationTest {

    MockMvc mvc;

    private Gson gson;

    @Autowired
    LoginUserArgumentResolver loginUserArgumentResolver;

    @Autowired
    private NewsController newsController;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @Autowired
    DatabaseCleaner databaseCleaner;

    static private Long savedNewsId;
    static private Long savedNewsImageId;
    static private Long memberId;
    static String memberEmail = "tester@gmail.com";
    static Role memberRole = Role.USER;
    static String memberPicture = "testerPicture";
    static String memberName = "JanuaryMan";

    @BeforeAll
    void beforeAll() throws Exception {

        mvc = MockMvcBuilders
                .standaloneSetup(newsController)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .setCustomArgumentResolvers(new MockLoginUserArgumentResolver())
                .build();

        //LocalDateTime 타입의 데이터가 json 형태로 넘어 왔을때, 배열 형태로 넘어오는데 해당 데이터를 Gson으로 파싱하여 LocalDateTime 변수에 다시
        //넣으려 할때 배열로 넘어온 LocalDateTime을 Gson이 제대로 인식하지 못해서 에러가 발생한다.
        //그러므로, 다음과 같이 LocalDateTime을 정상적으로 파싱하기위해 Gson에 Adapter를 추가등록한다.
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            }
        }).create();

        databaseCleaner.execute(); //entity로 인해 존재하는 테이블들을 모두 truncate 시킴
        createMember(); //테스트에서 사용 될 멤버 등록
    }

    @AfterAll
    void afterAll() {
        databaseCleaner.execute(); //entity로 인해 존재하는 테이블들을 모두 truncate 시킴
    }


    @Order(1)
    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"}) //권한 통과용
    @DisplayName("뉴스 등록 성공_1")
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
        ResultActions actions = mvc.perform(multipart("/api/auth/news")
                .file(newsPostDTO)
                .file(newsImage));

        //then
        actions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("201"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.newsId").isNumber());

        System.out.println("등록 : "+actions.andReturn().getResponse().getContentAsString());
        SingleResponseDto<NewsDto.Response> response = gson.fromJson(actions.andReturn().getResponse().getContentAsString(), new TypeToken<SingleResponseDto<NewsDto.Response>>(){}.getType());
        this.savedNewsId = response.getData().getNewsId();
        this.savedNewsImageId = response.getData().getNewsImages().get(0).getImageId();

    }

    @Transactional
    @Order(2)
    @DisplayName("뉴스 등록 실패 - 인가 X_2")
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
        ResultActions actions = mvc.perform(multipart("/api/auth/news")
                .file(newsPostDTO)
                .file(newsImage));

        //then
        actions.andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("401"));
    }


    @Order(3)
    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER", "ADMIN"})
    @DisplayName("뉴스 이미지 삭제 성공_3")
    @Test
    void 뉴스이미지삭제_성공() throws Exception {

        //given
        //when
        ResultActions actions = mvc.perform(delete("/api/auth/newsImg/{id}", savedNewsImageId));

        //then
        actions
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(savedNewsImageId.longValue()));
        System.out.println(actions.andReturn().getResponse().getContentAsString());
    }


    @Order(4)
    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"}) //권한 통과용
    @DisplayName("뉴스 수정 성공_4")
    @Test
    public void 뉴스수정_성공() throws Exception {

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


        MockHttpServletRequestBuilder builder = multipart("/api/auth/news/{id}", savedNewsId)
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

        //when
        ResultActions actions = mvc.perform(builder);

        //then
        System.out.println("수정 : "+actions.andReturn().getResponse().getContentAsString());
        actions
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.newsId").value(savedNewsId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.newsTitle").value(newsPut.getNewsTitle()));

        SingleResponseDto<NewsDto.Response> response = gson.fromJson(actions.andReturn().getResponse().getContentAsString(), new TypeToken<SingleResponseDto<NewsDto.Response>>(){}.getType());
        this.savedNewsImageId = response.getData().getNewsImages().get(0).getImageId();
    }


    @Order(5)
    @WithMockUser(username = "테스트계정", password = "테스트계정_비밀번호", roles = {"USER","ADMIN"})
    @DisplayName("뉴스 삭제 성공_5")
    @Test
    void 뉴스삭제_성공() throws Exception {

        //given
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


    private Long createMember() {
        Member savedMember = memberRepository.save(delegateMember());
        NewsIntegrationTest.memberId = savedMember.getMemberId();
        return savedMember.getMemberId();
    }

    private Member delegateMember() {

        return Member.builder()
                .memberEmail(this.memberEmail)
                .role(this.memberRole)
                .memberPicture(this.memberPicture)
                .memberName(this.memberName)
                .build();
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
                    .memberPicture(NewsIntegrationTest.memberPicture)
                    .memberName(NewsIntegrationTest.memberName)
                    .memberEmail(NewsIntegrationTest.memberEmail)
                    .build();

            return new SessionUser(member);
        }
    }

}
