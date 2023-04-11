package my.project.wenews.annotation;

import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidExtValidator implements ConstraintValidator<ValidExt, MultipartFile> {

    List<String> possibleExt = List.of("jpeg","jpg","gif","png");
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        if(value == null)
            return true;

        String fileExtension = Files.getFileExtension(value.getOriginalFilename());

        if (possibleExt.contains(fileExtension)) {
            return true;
        } else {
            return false;
        }
    }
}
