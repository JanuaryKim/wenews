package my.project.wenews.news.controller;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.member.entity.Member;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.dto.SingleResponseDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.mapper.NewsMapper;
import my.project.wenews.news.service.NewsImageService;
import my.project.wenews.news.service.NewsService;
import my.project.wenews.security.auth.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Validated
public class NewsController {

    private final NewsService newsService;
    private final NewsImageService newsImageService;
    private final NewsMapper newsMapper;

    @PostMapping(value = "/api/auth/news", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postNews(
            @RequestPart(value = "news-dto") @Valid NewsDto.Post newsPost,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages

    ) throws IOException {
        Member member = new Member(1L);
        News news = newsMapper.newsDtoPostToNews(newsPost, member); //태그 제외 사항을 변환
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPost); //태그만 따로 변환
        News createdNews = newsService.createNews(tagAddedNews, newsImages); //News 생성
        NewsDto.SimpleResponse simpleResponse = newsMapper.newsToNewsDtoSimpleResponse(createdNews); //Response 형태로 변환
        SingleResponseDto<NewsDto.SimpleResponse> response = new SingleResponseDto<>(200, simpleResponse);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


    @GetMapping(value = "/api/news/{id}")
    public ResponseEntity getNews(@PathVariable("id") @Positive Long newsId) {

        News news = newsService.readNews(newsId); //뉴스 데이터 읽음
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(news);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, news);
        SingleResponseDto<NewsDto.Response> singleResponseDto = new SingleResponseDto<>(200, response);
        return new ResponseEntity(singleResponseDto,HttpStatus.OK);
    }


    @PutMapping(value = "/api/auth/news/{id}",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity modifyNews(
            @PathVariable("id") Long newsId,
            @RequestPart(value = "news-dto") @Valid NewsDto.Put newsPut,
            @RequestPart(value = "news-images", required = false) MultipartFile newsImages,
            @LoginUser SessionUser user) throws IOException {

        Member member = new Member(1L);
        News news = newsMapper.newsDtoPutToNews(newsPut, member);
        News tagAddedNews = newsMapper.newsTagArrToNewsTagStr(news, newsPut);
        News updatedNews = newsService.updateNews(tagAddedNews, newsId, newsImages, user.getId());
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(updatedNews);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, updatedNews);
        SingleResponseDto<NewsDto.Response> singleResponseDto = new SingleResponseDto<>(200, response);
        return new ResponseEntity(singleResponseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth/news/{id}")
    public ResponseEntity removeNews(
            @PathVariable("id") @Positive Long newsId,
            @LoginUser SessionUser user){

        newsService.deleteNews(newsId, user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth/newsImg/{id}")
    public ResponseEntity removeNewsImg( //뉴스에 첨부된 이미지만 삭제
            @PathVariable("id") @Positive Long newsId,
            @LoginUser SessionUser user) throws IOException {
        newsImageService.deleteNewsImg(newsId, user.getId());
        SingleResponseDto<Long> singleResponseDto = new SingleResponseDto<>(HttpStatus.OK.value(), newsId);
        return new ResponseEntity(singleResponseDto,HttpStatus.OK);
    }
}
