package com.semihbkgr.corbeau.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@Slf4j
public class ValidationConfig {

    private static final String DEFAULT_MESSAGE_SOURCE_PATH = "classpath:messages";
    private static final String DEFAULT_MESSAGE_SOURCE_ENCODEING = "UTF-8";

    private final String messageSourcePath;
    private final String messageSourceEncoding;

    @Autowired
    public ValidationConfig(@Value("${validation.message.source.path:#{null}}") String messageSourcePath,
                            @Value("${validation.message.source.encoding:#{null}}") String messageSourceEncoding) {
        this.messageSourcePath = messageSourcePath != null ?
                messageSourcePath : DEFAULT_MESSAGE_SOURCE_PATH;
        this.messageSourceEncoding = messageSourceEncoding != null ?
                messageSourceEncoding : DEFAULT_MESSAGE_SOURCE_ENCODEING;
        log.info("Message Source Path: {}", messageSourcePath);
        log.info("Message Source Encoding: {}", messageSourceEncoding);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(messageSourcePath);
        messageSource.setDefaultEncoding(messageSourceEncoding);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

}
