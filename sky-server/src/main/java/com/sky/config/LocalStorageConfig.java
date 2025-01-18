package com.sky.config;

import com.sky.properties.LocalStorageProperties;
import com.sky.utils.LocalStorageUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
@Slf4j
public class LocalStorageConfig {


    @Bean
    @ConditionalOnMissingBean
    public LocalStorageUtil localStoragePath(LocalStorageProperties localStorageProperties) {
        log.info("Start build file upload object: {}", localStorageProperties);
        return new LocalStorageUtil(localStorageProperties.getPath());
    }
}
