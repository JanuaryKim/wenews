package my.project.wenews.news.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.project.wenews.entity.BaseTimeEntity;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;


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
}
