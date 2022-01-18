package com.alikmndlu.userservice;

import com.alikmndlu.userservice.controller.UserController;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.alikmndlu.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceApplicationTests {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void getUserByIdTest(){
        String token = getToken();

        String userId1 = "61e690ebf5d6d448f04fce06";
        String userId2 = "61e690ebf5d6d448f04fce07";

//         without token
        ResponseEntity<User> testResponse = restTemplate.exchange(
                "http://localhost:8080/api/users/" + userId1,
                HttpMethod.GET,
                null,
                User.class
        );
        System.out.println(testResponse.getStatusCodeValue());
        assertEquals(401, testResponse.getStatusCodeValue());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // with valid token but getting another user
        testResponse = restTemplate.exchange(
                "http://localhost:8080/api/users/" + userId2,
                HttpMethod.GET,
                entity,
                User.class
        );
        assertEquals(403, testResponse.getStatusCodeValue());

        // with token and get valid user
        testResponse = restTemplate.exchange(
                "http://localhost:8080/api/users/" + userId1,
                HttpMethod.GET,
                entity,
                User.class
        );
        assertEquals(200, testResponse.getStatusCodeValue());
        assertEquals("alikmndlu1@gmail.com", testResponse.getBody().getEmailAddress());
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
