package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;

import my.project.wenews.exception.BusinessException;
import my.project.wenews.exception.ExceptionCode;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import my.project.wenews.news.repository.NewsImageRepository;
import my.project.wenews.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileService fileService;
    private final NewsImageRepository newsImageRepository;

    @Value("${path.connect-path}")
    private String connectPath;

    @Value("${path.file-path}")
    private String filePath;

    public News createNews(News news, MultipartFile newsImage) throws IOException {
        News savedNews = newsRepository.save(news); //뉴스 데이터 저장

        if(newsImage != null){
            String fileName = fileService.createFileName(news.getNewsId(), newsImage); //저장할 이미지의 파일명 정의
            NewsImage savedNewsImage = newsImageRepository.save(delegateCreateNewsImage(savedNews, connectPath + news.getNewsId() + "/" + fileName));//뉴스 이미지 데이터 저장
            savedNews.setNewsImages(List.of(savedNewsImage));
            fileService.saveFile(filePath + savedNews.getNewsId(), fileName, newsImage); //뉴스 이미지 저장
        }

        return savedNews;
    }

    public News readNews(Long newsId) {

        return verifyExistsNews(newsId);
    }

    public News updateNews(News updateNews, Long id, MultipartFile newsImg, Long memberId) throws IOException {

        News readNews = verifyExistsNews(id); //뉴스가 존재하는지 검증
        verifyRegister(readNews, memberId);

        if (newsImg != null && newsImageRepository.findNewsImagesByNews(readNews).size() > 0) { //이미 이미지가 존재하다면 추가로 이미지 등록 불가
            throw new BusinessException(ExceptionCode.ALREADY_EXISTS_NEWS_IMG);
        }
        readNews.updateNews(updateNews); //뉴스의 데이터 수정

        if (newsImg != null) {
            String fileName = fileService.createFileName(readNews.getNewsId(), newsImg); //저장할 이미지의 파일명 정의
            NewsImage savedNewsImage = newsImageRepository.save(delegateCreateNewsImage(readNews, connectPath + readNews.getNewsId() + "/" + fileName)); //뉴스 이미지 데이터 저장
            readNews.setNewsImages(List.of(savedNewsImage));
            fileService.saveFile(filePath + readNews.getNewsId(), fileName, newsImg); //뉴스 이미지 실제 저장
        }

        return readNews;
    }

    private News verifyExistsNews(Long id) {

        return newsRepository.findById(id).orElseThrow(()-> new RuntimeException("존재하지 않는 뉴스"));
    }

    @Transactional(readOnly = true)
    public List<News> readAllNewsDesc() {
        List<News> newsList = newsRepository.findAll(Sort.by(Sort.Direction.DESC, "newsId"));
        return newsList;
    }

    public void deleteNews(Long id, Long memberId) {

        News readNews = newsRepository.findById(id).orElseThrow(() -> new RuntimeException());

        verifyRegister(readNews, memberId);
        newsRepository.delete(readNews);
    }

    private NewsImage delegateCreateNewsImage(News news, String path) {

        NewsImage newsImage = NewsImage.builder().news(news).url(path).build();

        return newsImage;
    }


    /** 정상적인 상황에서 버튼 활성화를 위한 검증 로직 **/
    public boolean verifyRegister(Long newsId, SessionUser user) {

        if (user == null) {
            return false;
        }

        Optional<News> news = newsRepository.findById(newsId);

        if (news.isPresent()) {
            if (news.get().getMember().getMemberId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    /** 비정상적인 상황에서 예외 발생을 위한 검증 로직 **/
    public void verifyRegister(News news, Long memberId) {

        if (!news.getMember().getMemberId().equals(memberId)){
            throw new BusinessException(ExceptionCode.NOT_AUTHOR_OF_NEWS);
        }
    }


}
