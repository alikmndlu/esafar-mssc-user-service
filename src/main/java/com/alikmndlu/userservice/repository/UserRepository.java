package com.alikmndlu.userservice.repository;

import com.alikmndlu.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAddressAndPassword(String emailAddress, String password);

    User findByEmailAddress(String emailAddress);
}
