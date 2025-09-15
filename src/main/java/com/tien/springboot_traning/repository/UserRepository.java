package com.tien.springboot_traning.repository;

import com.tien.springboot_traning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
    Optional<User> findUserByName(String name);
}
