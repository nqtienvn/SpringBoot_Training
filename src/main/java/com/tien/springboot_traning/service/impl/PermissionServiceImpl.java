package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.PermissionRequest;
import com.tien.springboot_traning.dto.response.PermissionResponse;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
import com.tien.springboot_traning.mapper.PermissionMapper;
import com.tien.springboot_traning.repository.PermissionRepository;
import com.tien.springboot_traning.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        return permissionMapper.toPermissionResponse(permissionRepository.save(permissionMapper.toPermission(permissionRequest)));
    }

    @Override
    public List<PermissionResponse> getAllPermission() {
        if(permissionRepository.findAll().isEmpty()) {
            throw new AppException(ErrorCode.EMPTY);
        }
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
