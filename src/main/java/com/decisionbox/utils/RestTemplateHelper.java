//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateHelper.class);
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    @Value("${FLOWABLE_BASE_PATH}")
    private String FLOWABLE_BASE_PATH;

    @Autowired
    public RestTemplateHelper(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class, uriVariables);
            JavaType javaType = this.objectMapper.getTypeFactory().constructType(clazz);
            return this.readValue(response, javaType);
        } catch (HttpClientErrorException var6) {
            if (var6.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.info("No data found {}", url);
            } else {
                LOGGER.info("rest client exception", var6.getMessage());
            }

            return null;
        }
    }

    public <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class, uriVariables);
            CollectionType collectionType = this.objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return (List)this.readValue(response, collectionType);
        } catch (HttpClientErrorException var6) {
            if (var6.getStatusCode() == HttpStatus.NOT_FOUND) {
                LOGGER.info("No data found {}", url);
            } else {
                LOGGER.info("rest client exception", var6.getMessage());
            }

            return null;
        }
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity(body);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class, uriVariables);
        JavaType javaType = this.objectMapper.getTypeFactory().constructType(clazz);
        return this.readValue(response, javaType);
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body) {
        HttpEntity<R> request = new HttpEntity(body);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class, new Object[0]);
        JavaType javaType = this.objectMapper.getTypeFactory().constructType(clazz);
        return this.readValue(response, javaType);
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, HttpHeaders headers) {
        HttpEntity request = new HttpEntity(body, headers);

        try {
            ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class, new Object[0]);
            JavaType javaType = this.objectMapper.getTypeFactory().constructType(clazz);
            return this.readValue(response, javaType);
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity(body);
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, request, String.class, uriVariables);
        JavaType javaType = this.objectMapper.getTypeFactory().constructType(clazz);
        return this.readValue(response, javaType);
    }

    public void delete(String url, Object... uriVariables) {
        try {
            this.restTemplate.delete(url, uriVariables);
        } catch (RestClientException var4) {
            LOGGER.info(var4.getMessage());
        }

    }

    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.CREATED) {
            LOGGER.info("No data found {}", response.getStatusCode());
        } else {
            try {
                if (response.getBody() != null) {
                    result = this.objectMapper.readValue((String)response.getBody(), javaType);
                } else {
                    result = (T) response.getStatusCode().toString();
                }
            } catch (IOException var5) {
                LOGGER.info(var5.getMessage());
            }
        }

        return result;
    }
}
