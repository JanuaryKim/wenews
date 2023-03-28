package my.project.wenews.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidTagsValidator.class})
public @interface ValidTags {

    String message() default "태그의 값이 유효하지 않습니다";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
