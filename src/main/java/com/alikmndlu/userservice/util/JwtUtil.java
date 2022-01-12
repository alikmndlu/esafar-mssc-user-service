package com.alikmndlu.userservice.util;

import com.alikmndlu.userservice.dto.UserIdEmailAddressDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public UserIdEmailAddressDto transferTokenToIdAndEmailAddress(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return UserIdEmailAddressDto.builder()
                .id(Long.parseLong(claims.get("userId").toString()))
                .emailAddress(claims.getSubject())
                .build();
    }
}
