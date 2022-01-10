package com.alikmndlu.userservice.repository;

import com.alikmndlu.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
