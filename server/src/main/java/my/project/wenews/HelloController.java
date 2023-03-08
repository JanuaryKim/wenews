package my.project.wenews;


import lombok.RequiredArgsConstructor;
import my.project.wenews.oauth2.Oauth2SuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloController {

    @GetMapping(value = "/")
    public ResponseEntity hello() {


        return new ResponseEntity("hello", HttpStatus.OK);
    }
}
