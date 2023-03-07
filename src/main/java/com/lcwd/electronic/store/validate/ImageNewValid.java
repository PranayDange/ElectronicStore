package com.lcwd.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNewValid {
    //error message
    String message() default "Invalid Image Name!!!";

    //represents group of constrainst

    Class<?>[] groups() default {};
    //additional information about the annotation

    Class<? extends Payload>[] payload() default {};

}
