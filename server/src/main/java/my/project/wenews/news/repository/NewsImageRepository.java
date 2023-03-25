package my.project.wenews.news.repository;

import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsImageRepository extends JpaRepository<NewsImage, Long> {


    public List<NewsImage> findNewsImagesByNews(News news);
}
