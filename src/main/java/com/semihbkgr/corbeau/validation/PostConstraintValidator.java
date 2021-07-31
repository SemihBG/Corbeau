package com.semihbkgr.corbeau.validation;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.validation.annotation.PostValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostConstraintValidator implements ConstraintValidator<PostValidation, Post> {

    @Override
    public boolean isValid(Post value, ConstraintValidatorContext context) {
        return value.getTitle().equals("value");
    }

}
