package my.project.wenews.news.service;

import my.project.wenews.member.entity.Member;
import my.project.wenews.member.stub.MemberStub;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Transactional
public class NewsServiceTest {


    @InjectMocks //해당 애노테이션이 붙은 객체는 생성하고, 객체 안에 존재하는 의존성들은 @Mock 이 붙은 객체들로 넣어 줌
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Test
    void testCreateNews() {

        Member member = MemberStub.memberList.get(0);
        News param = News.builder()
                .member(member)
                .newsTitle("테스트 제목")
                .newsContents("테스트 내용").newsTags("|C#|Java|")
                .build();

        News expected = News.newInstance(param);
        expected.setNewsId(1L);

        given(newsRepository.save(param)).willReturn(expected);

//        News actual = newsService.createNews(param);

        //usingRecursiveComparison : 클래스 내부의 값들을 비교함 (주소값이 같은지 확인하는것이 아님)
        //그러므로, 값만 같으면 되는 것
//        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
