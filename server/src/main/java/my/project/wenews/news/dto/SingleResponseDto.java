package my.project.wenews.news.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SingleResponseDto <T>{

    int status = 200;
    T data;


//    @Override
//    public String toString() {
//        return "SingleResponseDto{" +
//                "status=" + status +
//                ", data=" + data +
//                '}';
//    }

    @Override
    public String toString() {
        return "SingleResponseDto{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
