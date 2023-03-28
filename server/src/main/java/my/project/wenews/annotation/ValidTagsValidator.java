package my.project.wenews.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTagsValidator implements ConstraintValidator<ValidTags, String[]> {

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {

        for (String tag : value) {
            if (tag != null) {

                if(tag.length() > 10)
                    return false;

                if(!tag.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]*"))
                    return false;

            }
        }

        return true;
    }
}
