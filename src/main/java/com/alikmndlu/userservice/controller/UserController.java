package com.alikmndlu.userservice.controller;

import com.alikmndlu.userservice.config.UrlConfig;
import com.alikmndlu.userservice.dto.LoginCredentialsDto;
import com.alikmndlu.userservice.dto.RegisterCredentialsDto;
import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.dto.UserIdEmailAddressDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.service.UserService;
import com.alikmndlu.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final UrlConfig urlConfig;

    private final String apiHost = "http://localhost:9001/api/users";

    private final JwtUtil jwtUtil;

    @GetMapping("/{user-id}")
    public ResponseEntity<User> findUser(
            @PathVariable("user-id") Long userId,
            @RequestHeader("Authorization") String token) {

        UserIdEmailAddressDto userInfo = jwtUtil.transferTokenToIdAndEmailAddress(token);
        if (!userInfo.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> user = userService.findUserById(userId);

        return user.isPresent() ?
                ResponseEntity.ok().body(user.get()) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<User> updateUser(@PathVariable("user-id") Long userId, @RequestBody Map<String, ?> updates) {
        return ResponseEntity.accepted().body(userService.updateUser(userId, updates));
    }

    // Delete User By Id
    @DeleteMapping("/{user-id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("user-id") Long userId,
            @RequestHeader("Authorization") String token) {

        UserIdEmailAddressDto userInfo = jwtUtil.transferTokenToIdAndEmailAddress(token);
        log.info("Delete User API {requestId: {},jwtId: {}, jwtEmailAddress: {}, result: {}}", userId, userInfo.getId(), userInfo.getEmailAddress(), userInfo.getId().equals(userId));
        if (!userInfo.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // Get USer With Addresses List
    @GetMapping("/addresses/{user-id}")
    public ResponseEntity<UserAddressesDto> findUserWithAddresses(
            @PathVariable("user-id") Long userId,
            @RequestHeader("Authorization") String token) {

        UserIdEmailAddressDto userInfo = jwtUtil.transferTokenToIdAndEmailAddress(token);
        log.info("Delete User API {requestId: {},jwtId: {}, jwtEmailAddress: {}, result: {}}", userId, userInfo.getId(), userInfo.getEmailAddress(), userInfo.getId().equals(userId));
        if (!userInfo.getId().equals(userId)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> user = userService.findUserById(userId);

        if (user.isPresent()) {
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

    // Find User By Email Address
    @GetMapping("/by-email/{email-address}")
    public ResponseEntity<User> findUserByEmailAddress(@PathVariable("email-address") String emailAddress) {
        User user = userService.findByEmailAddress(emailAddress);
        System.out.println("Request is Here");
        return ResponseEntity.ok().body(user);
    }
}
