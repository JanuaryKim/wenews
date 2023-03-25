package my.project.wenews.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SingleResponseDto <T>{

    int status = 200;
    T data;
}
