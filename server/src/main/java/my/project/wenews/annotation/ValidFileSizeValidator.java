package my.project.wenews.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidFileSizeValidator implements ConstraintValidator<ValidFileSize, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        if (value != null && value.getSize() > 20971520) {
            return false;
        }
        return true;
    }
}
