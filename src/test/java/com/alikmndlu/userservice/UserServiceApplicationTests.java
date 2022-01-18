package com.alikmndlu.userservice;

import com.alikmndlu.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceApplicationTests {

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void contextLoads() {
    }

    @Test
    void getUserByIdTest(){

        String token = getToken();

        ResponseEntity<User> testResponse = restTemplate.exchange(
                "http://localhost:8080/api/users/1",
                HttpMethod.GET,
                null,
                User.class
        );
        assertTrue(testResponse.getStatusCode().is4xxClientError());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        testResponse = restTemplate.exchange(
                "http://localhost:8080/api/users/1",
                HttpMethod.GET,
                entity,
                User.class
        );
        assertTrue(testResponse.getStatusCode().is2xxSuccessful());
        assertTrue(testResponse.getBody().getEmailAddress().equals("alikmndlu1@gmail.com"));
    }


    String getToken(){
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
                Map.of(
                        "emailAddress", "alikmndlu1@gmail.com",
                        "password", "123"
                )
        );
        ResponseEntity<Map> loginResponse = restTemplate.exchange(
                "http://localhost:8080/api/auth/login",
                HttpMethod.POST,
                entity,
                Map.class
        );
        return loginResponse.getBody().get("token").toString();
    }

}
