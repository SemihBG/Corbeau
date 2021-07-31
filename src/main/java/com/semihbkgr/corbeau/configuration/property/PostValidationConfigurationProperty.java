package com.semihbkgr.corbeau.configuration.property;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("validation.post")
public class PostValidationConfigurationProperty {

    private String valid;

}
