package com.alikmndlu.userservice;

import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.alikmndlu.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository
    ) {
        return args -> {
            List<User> users = userRepository.findAll();
            if (ObjectUtils.isEmpty(users)) {
                userRepository.save(User.builder()
                        .name("Ali Kmndlu")
                        .emailAddress("alikmndlu1@gmail.com")
                        .password("123")
                        .build());
            }
        };
    }
}
