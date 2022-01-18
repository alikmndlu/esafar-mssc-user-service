package com.alikmndlu.userservice.service.impl;

import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.model.User;
import com.alikmndlu.userservice.repository.UserRepository;
import com.alikmndlu.userservice.service.UserService;
import com.alikmndlu.userservice.util.StaticUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(StaticUtils.md5Encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        boolean isUserExists = userRepository.existsById(userId);
        if (isUserExists)
            userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userId, Map<String, ?> updates) {
        //todo update user impl
        return null;
    }

    @Override
    public UserAddressesDto findUserWithAddresses(User user) {
        //todo impl method
        return null;
    }

    @Override
    public boolean checkUserExistence(String emailAddress, String password) {
        password = StaticUtils.md5Encrypt(password);
        return userRepository.existsByEmailAddressAndPassword(emailAddress, password);
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public String testHelloWorld() {
        return "Hello World!";
    }
}
