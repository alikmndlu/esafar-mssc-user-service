package com.alikmndlu.userservice.controller;

import com.alikmndlu.userservice.dto.LoginCredentialsDto;
import com.alikmndlu.userservice.dto.RegisterCredentialsDto;
import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.dto.UserIdEmailAddressDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.service.UserService;
import com.alikmndlu.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

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

    // Get User By Id
    @GetMapping("/{user-id}")
    public ResponseEntity<User> findUser(@PathVariable("user-id") String userId, @RequestHeader("Authorization") String token) {
        checkIsValidUserSendRequest(token, userId);
        Optional<User> user = userService.findUserById(userId);
        log.info("Get User By API Called {requestId: {}, result: {}}", userId, user.isPresent());

        // If Found
        if (user.isPresent()){
            return ResponseEntity.ok().body(user.get());
        }

        // 404 Error, If Not Found
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Check User Existence API
    @PostMapping("/check-existence")
    public ResponseEntity<Boolean> checkUserExistence(@RequestBody LoginCredentialsDto loginDto) {
        return ResponseEntity.ok().body(
                userService.checkUserExistence(loginDto.getEmailAddress(), loginDto.getPassword())
        );
    }

    // Find User By Email Address
    @GetMapping("/by-email/{email-address}")
    public ResponseEntity<User> findUserByEmailAddress(@PathVariable("email-address") String emailAddress) {
        User user = userService.findByEmailAddress(emailAddress);
        return ResponseEntity.ok().body(user);
    }

    private void checkIsValidUserSendRequest(String token, String userId){
        UserIdEmailAddressDto userIdEmailAddressDto = jwtUtil.getSubjectOfToken(token);
        if (!userId.equals(userIdEmailAddressDto.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
