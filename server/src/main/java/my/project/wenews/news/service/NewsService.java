package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.repository.NewsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public News updateNews(News updateNews, Long id) {

        News readNews = verifyExistsNews(id);

        return readNews.updateNews(updateNews);
    }

    private News verifyExistsNews(Long id) {


        return newsRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 뉴스"));
    }

    @Transactional(readOnly = true)
    public List<News> readAllNewsDesc() {
        List<News> newsList = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "newsId"));
        return newsList;
    }
}
