package com.semihbkgr.corbeau.validation.annotation;

import com.semihbkgr.corbeau.validation.PostConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostConstraintValidator.class)
public @interface PostValidation {

    String message() default "not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
