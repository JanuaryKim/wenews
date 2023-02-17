package my.project.wenews.news.entity;

import lombok.*;
import my.project.wenews.member.entity.Member;
import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
