package com.example.htmllandingservice.dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class Auth0RequestBn {
    @JsonProperty("client_id")
    public String clientId;
    @JsonProperty("client_secret")
    public String clientSecret;
    @JsonProperty("audience")
    public String audience;
    @JsonProperty("grant_type")
    public String grantType;
}
