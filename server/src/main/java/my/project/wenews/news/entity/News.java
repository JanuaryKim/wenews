package my.project.wenews.news.entity;

import lombok.*;
import my.project.wenews.member.entity.Member;
import javax.persistence.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 20)
    private String newsTitle;

    @Column(columnDefinition = "TEXT")
    private String newsContents;

    @Column(length = 100)
    private String newsTags;

    public static News newInstance(News news) {
        return new News(news);
    }

    public News(News news) {
        this.newsId = news.newsId;
        this.member = new Member(news.member);
        this.newsTitle = news.newsTitle;
        this.newsContents = news.newsContents;
        this.newsTags = news.getNewsTags();
    }
}
