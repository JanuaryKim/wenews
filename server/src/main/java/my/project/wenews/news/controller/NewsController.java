package my.project.wenews.news.controller;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.entity.Member;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.SingleResponseDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import my.project.wenews.news.mapper.NewsMapper;
import my.project.wenews.news.service.NewsImageService;
import my.project.wenews.news.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;
    private final NewsImageService newsImageService;
    private final NewsMapper newsMapper;

    @PostMapping(value = "/api/auth/news", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postNews(
            @RequestPart(value = "news-dto") NewsDto.Post newsPost,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages

    ) throws IOException {
        Member member = new Member(1L);
        News news = newsMapper.newsDtoPostToNews(newsPost, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPost);
        News createdNews = newsService.createNews(tagAddedNews, newsImages);
        NewsDto.SimpleResponse simpleResponse = newsMapper.newsToNewsDtoSimpleResponse(createdNews);
        SingleResponseDto<NewsDto.SimpleResponse> response = new SingleResponseDto<>(200, simpleResponse);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


    @GetMapping(value = "/api/news/{id}")
    public ResponseEntity getNews(@PathVariable Long id) {

        News news = newsService.readNews(id); //뉴스 데이터 읽음
        List<String> urls = newsImageService.readNewsImagesURL(news);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(news);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, news);
        response.setNewsImagesURL(urls);
        SingleResponseDto<NewsDto.Response> singleResponseDto = new SingleResponseDto<>(200, response);
        return new ResponseEntity(singleResponseDto,HttpStatus.OK);
    }


    @PutMapping(value = "/api/auth/news/{id}",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity modifyNews(
            @PathVariable Long id,
            @RequestPart(value = "news-dto") NewsDto.Put newsPut,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages) {

        Member member = new Member(1L);
        News news = newsMapper.newsDtoPutToNews(newsPut, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPut);
        News updatedNews = newsService.updateNews(tagAddedNews, id);
        List<String> urls = newsImageService.readNewsImagesURL(updatedNews);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(updatedNews);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, updatedNews);
        response.setNewsImagesURL(urls);
        SingleResponseDto<NewsDto.Response> singleResponseDto = new SingleResponseDto<>(200, response);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth/news/{id}")
    public ResponseEntity removeNews(
            @PathVariable Long id){

        newsService.deleteNews(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
