package com.iamneo.lms.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamneo.lms.lms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

}
