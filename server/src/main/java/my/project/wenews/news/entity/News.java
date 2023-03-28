package my.project.wenews.news.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import my.project.wenews.entity.BaseTimeEntity;
import my.project.wenews.member.entity.Member;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class News extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 20)
    private String newsTitle;

    @Column(columnDefinition = "TEXT")
    private String newsContents;

    @Column(length = 100)
    private String newsTags;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    List<NewsImage> newsImages = new ArrayList<>();

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

    public News updateNews(News updateNews) {

        this.newsTitle = updateNews.newsTitle;
        this.newsContents = updateNews.newsContents;
        this.newsTags = updateNews.newsTags;

        return this;
    }
}
