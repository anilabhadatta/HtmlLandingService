package com.example.htmllandingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Setter
@Getter
public class VaultConfigBn {
    @JsonProperty("dbUrl")
    public String dbUrl;
    @JsonProperty("dbUser")
    public String dbUser;
    @JsonProperty("dbPassword")
    public String dbPassword;
}
