package my.project.wenews.news.entity;

import my.project.wenews.member.entity.Member;

import javax.persistence.*;
import java.sql.Clob;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 20)
    private String news_title;

    @Column(columnDefinition = "TEXT")
    private String news_content;

    @Column(length = 100)
    private String news_tag;

}
