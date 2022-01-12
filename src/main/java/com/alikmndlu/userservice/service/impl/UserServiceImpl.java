package com.alikmndlu.userservice.service.impl;

import com.alikmndlu.userservice.config.UrlConfig;
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

    private final UrlConfig urlConfig;

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
//        ResponseEntity<AddressDto[]> response = restTemplate.getForEntity(
//                urlConfig.getAddressServiceBaseUrl() + "/user/" + user.getId(),
//                AddressDto[].class
//        );
//
//        return UserAddressesDto.builder()
//                .user(user)
//                .addresses(response.getBody())
//                .build();

        return null;
    }

    @Override
    public boolean checkUserExistence(String emailAddress, String password) {
        return userRepository.existsByEmailAddressAndPassword(emailAddress, password);
    }
}
