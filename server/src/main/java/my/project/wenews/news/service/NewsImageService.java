package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import my.project.wenews.exception.BusinessException;
import my.project.wenews.exception.ExceptionCode;
import my.project.wenews.news.entity.News;
import my.project.wenews.news.entity.NewsImage;
import my.project.wenews.news.repository.NewsImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


@RequiredArgsConstructor
@Transactional
@Service
public class NewsImageService {

    private final NewsImageRepository repository;
    private final FileService fileService;



    public Long deleteNewsImg(Long imgId) throws IOException {

        NewsImage newsImage = verifyExistsImg(imgId); //존재하는 이미지인지 검증
        repository.deleteById(imgId); //이미지의 DB 데이터 삭제
        deleteNewsImageFiles(newsImage); //실제 이미지 파일 삭제
        return imgId;
    }

    public NewsImage verifyExistsImg(Long imgId) {
        return repository.findById(imgId).orElseThrow(() -> new BusinessException(ExceptionCode.NOT_EXISTS_NEWS_IMG));
    }

    private void deleteNewsImageFiles(NewsImage deleteNewsImage) throws IOException {

        String fileURL = deleteNewsImage.getUrl();
        String fileName = fileURL.substring(fileURL.lastIndexOf("/"));
        fileService.removeFile(deleteNewsImage.getNews().getNewsId(), fileName);
    }






}
