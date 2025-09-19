package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.RoleRequest;
import com.tien.springboot_traning.dto.response.PermissionResponse;
import com.tien.springboot_traning.dto.response.RoleResponse;
import com.tien.springboot_traning.entity.Permission;
import com.tien.springboot_traning.entity.Role;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
import com.tien.springboot_traning.mapper.RoleMapper;
import com.tien.springboot_traning.repository.PermissionRepository;
import com.tien.springboot_traning.repository.RoleRepository;
import com.tien.springboot_traning.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        //lấy ra list permission trong role với set các name của roleReuest
        Set<String> permissionName = roleRequest.getPermissionsName();
        Set<Permission> permissions = new HashSet<>();
        permissionName.forEach(permission -> {permissions.add(permissionRepository.findById(permission).get());});
        role.setPermissions(permissions);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> listRole() {
        if(roleRepository.findAll().isEmpty()) {
            throw new AppException(ErrorCode.EMPTY);
        }
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
