package com.semihbkgr.corbeau.validation;

import com.semihbkgr.corbeau.configuration.property.PostValidationConfigurationProperty;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.validation.annotation.PostValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostConstraintValidator implements ConstraintValidator<PostValidation, Post> {

    private final PostValidationConfigurationProperty validationConfigurationProperty;

    public PostConstraintValidator(PostValidationConfigurationProperty validationConfigurationProperty) {
        this.validationConfigurationProperty=validationConfigurationProperty;
    }

    @Override
    public boolean isValid(Post value, ConstraintValidatorContext context) {
        return value.getTitle().equals(validationConfigurationProperty.getValid());
    }

}
