package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import my.project.wenews.news.repository.NewsImageRepository;
import my.project.wenews.news.repository.NewsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileService fileService;
    private final NewsImageRepository newsImageRepository;


    public News createNews(News news, MultipartFile newsImage) throws IOException {
        News savedNews = newsRepository.save(news); //뉴스 데이터 저장
        String dirPath = fileService.createPath(savedNews.getNewsId());
        String fileName = fileService.createFileName(news.getNewsId(), newsImage);
        newsImageRepository.save(delegateCreateNewsImage(savedNews, "/imagePath" + "/" + news.getNewsId() + "/" + fileName)); //뉴스 이미지 데이터 저장
        fileService.saveFile(dirPath, fileName, newsImage); //뉴스 이미지 저장
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

    public void deleteNews(Long id) {

        News readNews = newsRepository.findById(id).orElseThrow(() -> new RuntimeException());
        newsRepository.delete(readNews);
    }

    private NewsImage delegateCreateNewsImage(News news, String path) {

        NewsImage newsImage = NewsImage.builder().news(news).url(path).build();

        return newsImage;
    }
}
