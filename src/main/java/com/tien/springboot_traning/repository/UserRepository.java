package com.tien.springboot_traning.repository;

import com.tien.springboot_traning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
}
