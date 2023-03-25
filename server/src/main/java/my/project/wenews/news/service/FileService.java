package my.project.wenews.news.service;

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

@Component
public class FileService {

    @Value("${path.resources.save-path}")
    private String savePath;

    public void saveFile(String dirPath, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(dirPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        File file = new File(dirPath + "/" + fileName);
        System.out.println(dirPath + "/" + fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createFileName(Long newsId, MultipartFile multipartFile){

        String originalFilename = multipartFile.getOriginalFilename(); // 파일 이름 전체
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1); // 확장자만 뽑아냄
        UUID uuid = UUID.randomUUID(); // 실제 파일명으로 쓰일 UUID
        String saveFileName = uuid + "." +ext;

        return saveFileName;
    }

    public String createPath(Long newsId) {
        String dirPath = savePath + "/" + newsId;
        return dirPath;
    }

}
