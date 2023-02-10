package my.project.wenews.news.controller;

import my.project.wenews.news.dto.NewsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    @RequestMapping(value = "/api/auth/news", method = RequestMethod.POST)
    public ResponseEntity postNews(@RequestPart(value = "news-dto", required = true) NewsDto.Post newsPost) {


        return new ResponseEntity("hello", HttpStatus.OK);

    }

}
