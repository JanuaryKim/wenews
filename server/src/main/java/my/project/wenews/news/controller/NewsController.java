package my.project.wenews.news.controller;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.entity.Member;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.mapper.NewsMapper;
import my.project.wenews.news.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @PostMapping(value = "/api/auth/news", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postNews(
            @RequestPart(value = "news-dto", required = true) NewsDto.Post newsPost,
            @RequestPart(required = false)MultipartFile[] newsImages

    ) {
        Member member = new Member(1L);
        News news = newsMapper.newsDtoPostToNews(newsPost, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPost);
        newsService.createNews(tagAddedNews);
        return new ResponseEntity("hello", HttpStatus.OK);
    }
}
