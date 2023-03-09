package my.project.wenews.news.repository;

import my.project.wenews.member.entity.Member;
import my.project.wenews.member.repository.MemberRepository;
import my.project.wenews.news.entity.News;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    void saveMember() {

        String id = "tester@google";
        String nickname = "테스터";
        String email = "tester@gmail.com";
        String password = "1234";
        byte age = 23;


        Member tester = Member.builder().memberId(id)
                .memberEmail(email)
                .memberAge(age)
                .memberPassword(password).build();

        memberRepository.save(tester);

    }

    @AfterAll
    void cleanTables() {
        memberRepository.deleteAll();
    }

    @Test
    void testFindNews() {

        String title = "테스트 제목";
        String contents = "테스트 내용";
        String tags = "|C#|Java|";

        LocalDateTime localDateTime = LocalDateTime.now();
        Member tester = Member.builder().memberId("tester@google").build();
        News news = News.builder()
                .newsTitle(title)
                .newsContents(contents)
                .member(tester)
                .newsTags(tags)
                .createdAt(localDateTime)
                .modifiedAt(localDateTime)
                .build();
        News savedNews = newsRepository.save(news);
        assertThat(savedNews.getNewsTitle()).isEqualTo(title);
        assertThat(savedNews.getNewsContents()).isEqualTo(contents);
        assertThat(savedNews.getNewsTags()).isEqualTo(tags);
    }

}
