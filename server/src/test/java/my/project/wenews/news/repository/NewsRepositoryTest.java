package my.project.wenews.news.repository;

import my.project.wenews.member.entity.Member;
import my.project.wenews.member.repository.MemberRepository;
import my.project.wenews.news.entity.News;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    void saveMember() {

        Long id = 1L;
        String nickname = "테스터";
        String email = "tester@gmail.com";
        String password = "1234";


        Member tester = Member.builder().memberId(id)
                .memberNickname(nickname)
                .memberEmail(email)
                .memberPassword(password).build();

        memberRepository.save(tester);
//
//        Assertions.assertThat(savedMember.getMemberId()).isEqualTo(id);
//        Assertions.assertThat(savedMember.getMemberNickname()).isEqualTo(nickname);
//        Assertions.assertThat(savedMember.getMemberEmail()).isEqualTo(email);
//        Assertions.assertThat(savedMember.getMemberPassword()).isEqualTo(password);

    }

    @Test
    void testFindNews() {

        String title = "테스트 제목";
        String contents = "테스트 내용";
        String tags = "|C#|Java|";

        Member tester = Member.builder().memberId(1L).build();
        News news = News.builder()
                .newsTitle(title)
                .newsContents(contents)
                .member(tester)
                .newsTags(tags).build();
        News savedNews = newsRepository.save(news);
        assertThat(savedNews.getNewsTitle()).isEqualTo(title);
        assertThat(savedNews.getNewsContents()).isEqualTo(contents);
        assertThat(savedNews.getNewsTags()).isEqualTo(tags);
    }

}
