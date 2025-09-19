package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.PermissionRequest;
import com.tien.springboot_traning.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest permissionRequest);

    List<PermissionResponse> getAllPermission();

    void deletePermission(String name);
}
