package com.alikmndlu.userservice.service;

import com.alikmndlu.userservice.dto.UserAddressesDto;
import com.alikmndlu.userservice.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(String userId);

    User createUser(User user);

    void deleteUser(String userId);

    User updateUser(String userId, Map<String, ?> updates);

    UserAddressesDto findUserWithAddresses(User user);

    boolean checkUserExistence(String emailAddress, String password);

    User findByEmailAddress(String emailAddress);

    void deleteAllUsers();

    String testHelloWorld();
}
