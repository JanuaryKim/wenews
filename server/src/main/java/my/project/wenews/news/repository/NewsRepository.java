package my.project.wenews.news.repository;

import my.project.wenews.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {


}
