package com.alikmndlu.userservice.controller;

import com.alikmndlu.userservice.config.UrlConfig;
import com.alikmndlu.userservice.dto.LoginCredentialsDto;
import com.alikmndlu.userservice.dto.RegisterCredentialsDto;
import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RestTemplate restTemplate;

    private final UserService userService;

    private final UrlConfig urlConfig;

    private final String apiHost = "http://localhost:9001/api/users";

    @GetMapping("/{user-id}")
    public ResponseEntity<User> findUser(@PathVariable("user-id") Long userId) {
        Optional<User> user = userService.findUserById(userId);

        return user.isPresent() ?
                ResponseEntity.ok().body(user.get()) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<User> updateUser(@PathVariable("user-id") Long userId, @RequestBody Map<String, ?> updates) {
        return ResponseEntity.accepted().body(userService.updateUser(userId, updates));
    }

    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/addresses/{user-id}")
    public ResponseEntity<UserAddressesDto> findUserWithAddresses(@PathVariable("user-id") Long userId) {
        Optional<User> user = userService.findUserById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(userService.findUserWithAddresses(user.get()));
        }
    }

    // Check User Existence API
    @PostMapping("/check-existence")
    public ResponseEntity<Boolean> checkUserExistence(@RequestBody LoginCredentialsDto loginDto) {
        return ResponseEntity.ok().body(
                userService.checkUserExistence(loginDto.getEmailAddress(), loginDto.getPassword())
        );
    }

    // Save New User API
    @PostMapping("/")
    public ResponseEntity<User> saveNewUser(@RequestBody RegisterCredentialsDto registerDto) {
        User user = userService.createUser(
                User.builder()
                        .name(registerDto.getName())
                        .emailAddress(registerDto.getEmailAddress())
                        .password(registerDto.getPassword())
                        .build()
        );
        return ResponseEntity.ok().body(user);
    }
}
