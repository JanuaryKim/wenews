package my.project.wenews.news.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileService {

    @Value("${path.file-path}")
    private String filePath;

    public void saveFile(String dirPath, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(dirPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        File file = new File(dirPath + "/" + fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFile(Long newsId, String fileName) throws IOException {
        String deleteFilePath = filePath + newsId + "/" + fileName;
        Path filePath = Paths.get(deleteFilePath);
        Files.delete(filePath);
    }

    public String createFileName(Long newsId, MultipartFile multipartFile){

        String originalFilename = multipartFile.getOriginalFilename(); // 파일 이름 전체
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // 확장자만 뽑아냄
        UUID uuid = UUID.randomUUID(); // 실제 파일명으로 쓰일 UUID
        String saveFileName = uuid + "." +ext;

        return saveFileName;
    }


}
