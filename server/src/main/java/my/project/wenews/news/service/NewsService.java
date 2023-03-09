package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.repository.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public News createNews(News news) {

        News savedNews = newsRepository.save(news);

        return savedNews;
    }

    public News readNews(Long newsId) {

        return verifyExistsNews(newsId);
    }

    public News updateNews(News updateNews) {

        News readNews = verifyExistsNews(updateNews.getNewsId());

        return readNews.updateNews(updateNews);
    }

    private News verifyExistsNews(Long id) {


        return newsRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 뉴스"));
    }


}
