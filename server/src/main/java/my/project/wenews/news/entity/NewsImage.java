package my.project.wenews.news.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import my.project.wenews.entity.BaseTimeEntity;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class NewsImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(length = 500)
    private String url;

    @ManyToOne
    @JoinColumn(name = "NEWS_ID")
    private News news;


}
