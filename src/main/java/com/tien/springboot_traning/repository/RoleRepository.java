package com.tien.springboot_traning.repository;

import com.tien.springboot_traning.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
