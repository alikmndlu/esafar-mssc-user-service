package com.alikmndlu.userservice.controller;

import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final String apiHost = "http://localhost:9001/api/users";

    @GetMapping("/{user-id}")
    public ResponseEntity<User> findUser(@PathVariable("user-id") Long userId) {
        Optional<User> user = userService.findUserById(userId);

        return user.isPresent() ?
                ResponseEntity.ok().body(user.get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user = userService.createUser(user);
        return ResponseEntity.created(URI.create(apiHost + "/" + user.getId())).body(user);
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
}
