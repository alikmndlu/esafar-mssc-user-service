package com.alikmndlu.userservice.util;

import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.alikmndlu.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Configuration
public class BeanFactory {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }


    // Data Initializer
//    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            UserService userService
    ) {
        return args -> {
            List<User> users = userRepository.findAll();
            if (ObjectUtils.isEmpty(users)) {
                userService.createUser(User.builder()
                        .name("Ali Kmndlu")
                        .emailAddress("alikmndlu1@gmail.com")
                        .password("123")
                        .build());
                log.info("New User Added {name: {}, emailAddress: {}, password: {}}", "Ali Kmndlu", "alikmndlu1@gmail.com", "123");

                userService.createUser(User.builder()
                        .name("Yones Rahimi")
                        .emailAddress("yones224@gmail.com")
                        .password("123")
                        .build());
                log.info("New User Added {name: {}, emailAddress: {}, password: {}}", "Yones Rahimi", "yones224@gmail.com", "123");
            }
        };
    }
}
