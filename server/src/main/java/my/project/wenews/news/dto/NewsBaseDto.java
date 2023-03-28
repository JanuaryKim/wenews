package my.project.wenews.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import my.project.wenews.annotation.ValidTags;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NewsBaseDto {

    @ValidTags
    private String[] newsTags;
}
