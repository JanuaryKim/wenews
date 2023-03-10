package my.project.wenews.page;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String index() {

        return "index"; //머스테치 스타터로인해 반환되는 String 앞뒤로 경로와 확장자가 자동으로 지정됨
    }

    @GetMapping(value = "/news/save")
    public String newsSave() {

        return "news-save";
    }
}
