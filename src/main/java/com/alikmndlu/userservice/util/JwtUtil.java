package com.alikmndlu.userservice.util;

import com.alikmndlu.userservice.dto.UserIdEmailAddressDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    public UserIdEmailAddressDto getSubjectOfToken(String token) {
        String userId = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        User user = userRepository.findById(userId).get();

        return UserIdEmailAddressDto.builder()
                .id(userId)
                .emailAddress(user.getEmailAddress())
                .build();
    }

}
