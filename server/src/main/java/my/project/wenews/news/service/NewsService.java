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
}
