package com.example.htmllandingservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
public class DbSettings {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDriverClassName;

}