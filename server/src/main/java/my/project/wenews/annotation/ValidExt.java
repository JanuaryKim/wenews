package my.project.wenews.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidExtValidator.class})
public @interface ValidExt {

    String message() default "유효하지 않은 확장자입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
