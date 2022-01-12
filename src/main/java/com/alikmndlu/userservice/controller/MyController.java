package com.alikmndlu.userservice.controller;

import com.alikmndlu.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class MyController {


    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok().body("Hello World");
    }
}
