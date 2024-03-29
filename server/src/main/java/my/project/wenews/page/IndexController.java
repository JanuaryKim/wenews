package my.project.wenews.page;


import lombok.RequiredArgsConstructor;
import my.project.wenews.annotation.ImageShown;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.news.dto.NewsDto;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.mapper.NewsMapper;
import my.project.wenews.news.service.NewsImageService;
import my.project.wenews.news.service.NewsService;
import my.project.wenews.security.auth.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final NewsService newsService;
    private final NewsImageService newsImageService;
    private final NewsMapper newsMapper;
    private final HttpSession httpSession;

    @GetMapping(value = "/")
    public String index(Model model, @LoginUser SessionUser user) {

        List<News> newsList = newsService.readAllNewsDesc();
        List<NewsDto.Response> responseList = newsMapper.newsListToNewsDtoResponseList(newsList);
        delegateConvertTag(newsList, responseList);
        model.addAttribute("news",responseList);

        if(user != null){
            model.addAttribute("userName", user.getName());
        }


        return "index"; //머스테치 스타터로인해 반환되는 String 앞뒤로 경로와 확장자가 자동으로 지정됨
    }

    @GetMapping(value = "/news/save")
    public String newsSave(Model model, @LoginUser SessionUser user) {

        if(user != null){
            model.addAttribute("userEmail", user.getEmail());
        }

        return "news-save";
    }

    @ImageShown
    @GetMapping(value = "/news/update/{id}")
    public String newsUpdate(@PathVariable Long id, Model model) {

        News news = newsService.readNews(id);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(news);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArrForUpdate(tempResponse, news);

        model.addAttribute("news",response);
        return "news-update";
    }

    @ImageShown
    @GetMapping(value = "/news/read/{id}")
    public String newsRead(@PathVariable Long id, Model model, @LoginUser SessionUser user) {

        News news = newsService.readNews(id);
        NewsDto.Response tempResponse = newsMapper.newsToNewsDtoResponse(news);
        NewsDto.Response response = newsMapper.newsTagStrToNewsTagArr(tempResponse, news);

        if (newsService.verifyRegister(id, user)) {
            model.addAttribute("register",true);
        }
        model.addAttribute("news",response);

        return "news-read";
    }

    private void delegateConvertTag(List<News> source, List<NewsDto.Response> destination) {
        for (int i = 0; i < source.size(); i++) {
            newsMapper.newsTagStrToNewsTagArr(destination.get(i), source.get(i));
        }
    }

}
