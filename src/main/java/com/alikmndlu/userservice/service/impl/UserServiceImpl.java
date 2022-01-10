package com.alikmndlu.userservice.service.impl;

import com.alikmndlu.userservice.dto.AddressDto;
import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.alikmndlu.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        boolean isUserExists = userRepository.existsById(userId);
        if (isUserExists)
            userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(Long userId, Map<String, ?> updates) {
        //todo update user impl
        return null;
    }

    @Override
    public UserAddressesDto findUserWithAddresses(User user) {
        ResponseEntity<AddressDto[]> response = restTemplate.getForEntity(
                "http://localhost:9002/api/addresses/user/1",
                AddressDto[].class
        );

        return UserAddressesDto.builder()
                .user(user)
                .addresses(response.getBody())
                .build();
    }
}
