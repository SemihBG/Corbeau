package com.semihbkgr.corbeau.configuration;

import com.semihbkgr.corbeau.configuration.property.PostValidationConfigurationProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.SpringWebConstraintValidatorFactory;

@Configuration
@EnableConfigurationProperties(PostValidationConfigurationProperty.class)
public class ValidationConfig {



}
