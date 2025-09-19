package com.tien.springboot_traning.repository;

import com.tien.springboot_traning.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
