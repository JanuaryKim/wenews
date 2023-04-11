package my.project.wenews.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidFileSizeValidator.class})
public @interface ValidFileSize {

    String message() default "파일의 크기가 20MB를 초과합니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
