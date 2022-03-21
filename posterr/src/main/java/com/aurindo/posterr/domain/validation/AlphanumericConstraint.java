package com.aurindo.posterr.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphanumericValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphanumericConstraint {

    String message() default "Only alphanumeric characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
