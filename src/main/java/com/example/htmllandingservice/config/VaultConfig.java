package com.example.htmllandingservice.config;

import com.example.htmllandingservice.dto.Request.Auth0RequestBn;
import com.example.htmllandingservice.dto.Response.Auth0ResponseBn;
import com.example.htmllandingservice.dto.VaultConfigBn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.google.gson.JsonParser;

import java.io.IOException;

@Configuration
public class VaultConfig {

    @Autowired
    Auth0RequestBn auth0RequestBn;

    @Value("${auth0.url}")
    private String auth0Url;
    @Value("${client_id}")
    public String clientId;
    @Value("${client_secret}")
    public String clientSecret;
    @Value("${audience}")
    public String audience;
    @Value("${grant_type}")
    public String grantType;
    @Value("${vault.url}")
    public String vaultUrl;

    private static final Logger logger = LoggerFactory.getLogger(VaultConfig.class);
    private final Gson gson;


    public VaultConfigBn vaultConfigBn;
    private final HttpClient client = HttpClients.custom().build();

    public VaultConfig(Gson gson) {
        this.gson = gson;
    }

    public VaultConfigBn populateVaultDataBn() throws IOException {
        generateAuth0RequestBn();
        String accessToken = generateJwtToken();
        getDbCreds(accessToken);
        return vaultConfigBn;
    }

    private String generateJwtToken() throws IOException {
        HttpPost httpPost = new HttpPost(auth0Url);
        ObjectMapper objectMapper = new ObjectMapper();
        String auth0RequestBnJson = objectMapper.writeValueAsString(auth0RequestBn);
        httpPost.setEntity(new StringEntity(auth0RequestBnJson));
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        HttpClientResponseHandler<String> responseHandler = response -> {
            logger.info("Response Code {}", response.getCode());
            String jsonResponse = EntityUtils.toString(response.getEntity());
            Auth0ResponseBn responseBn = objectMapper.readValue(jsonResponse, Auth0ResponseBn.class);
            response.close();
            return responseBn.getAccessToken();
        };

        return client.execute(httpPost, responseHandler);
    }


    private void generateAuth0RequestBn(){
        auth0RequestBn.setAudience(audience);
        auth0RequestBn.setClientId(clientId);
        auth0RequestBn.setGrantType(grantType);
        auth0RequestBn.setClientSecret(clientSecret);
    }

    private void getDbCreds(String accessToken) throws IOException {
        HttpGet httpGet = new HttpGet(vaultUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        httpGet.addHeader("Authorization", "Bearer "+accessToken);
        httpGet.addHeader("Accept", "application/json");
        HttpClientResponseHandler<String> responseHandler = response -> {
            logger.info("Response Code {}", response.getCode());
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject messageData = jsonObject.getAsJsonObject("message");
            vaultConfigBn = gson.fromJson(messageData, VaultConfigBn.class);
            response.close();
            return "";
        };

        client.execute(httpGet, responseHandler);
    }
}
