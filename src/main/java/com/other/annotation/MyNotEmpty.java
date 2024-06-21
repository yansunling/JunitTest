package com.other.annotation;

import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyNotEmpty {
    String message() default"不为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
