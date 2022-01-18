package com.alikmndlu.userservice.repository;

import com.alikmndlu.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmailAddressAndPassword(String emailAddress, String password);

    User findByEmailAddress(String emailAddress);
}
