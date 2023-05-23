package com.cos.jwt.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.jwt.jwt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
        public User findByUsername(String username);
}
