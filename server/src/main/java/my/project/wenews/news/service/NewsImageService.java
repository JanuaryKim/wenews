package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import my.project.wenews.news.repository.NewsImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class NewsImageService {

    private final NewsImageRepository repository;


    public List<String> readNewsImagesURL(News news) {

        List<NewsImage> newsImages = readNewsImage(news);

        if (newsImages != null) {
            return newsImages.stream().map(newsImage -> newsImage.getUrl()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private List<NewsImage> readNewsImage(News news) {

        List<NewsImage> newsImagesByNews = repository.findNewsImagesByNews(news);

        return newsImagesByNews;
    }
}
