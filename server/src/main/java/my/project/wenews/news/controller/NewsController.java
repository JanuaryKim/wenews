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
            @RequestPart(value = "news-dto") NewsDto.Post newsPost,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages

    ) {
        Member member = new Member("tester@google");
        News news = newsMapper.newsDtoPostToNews(newsPost, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPost);
        News createdNews = newsService.createNews(tagAddedNews);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(createdNews);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, createdNews);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }


    @GetMapping(value = "/api/news/{id}")
    public ResponseEntity getNews(@PathVariable Long id) {

        News news = newsService.readNews(id);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(news);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, news);

        return new ResponseEntity(response,HttpStatus.OK);
    }


    @PutMapping(value = "/api/auth/news/{id}",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity modifyNews(
            @PathVariable Long id,
            @RequestPart(value = "news-dto") NewsDto.Put newsPut,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages) {

        Member member = new Member("tester@google");
        News news = newsMapper.newsDtoPutToNews(newsPut, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPut);
        News updatedNews = newsService.updateNews(tagAddedNews, id);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(updatedNews);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, updatedNews);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth/news/{id}")
    public ResponseEntity removeNews(
            @PathVariable Long id){

        newsService.deleteNews(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
