package com.tien.springboot_traning.mapper;

import com.tien.springboot_traning.dto.request.PermissionRequest;
import com.tien.springboot_traning.dto.response.PermissionResponse;
import com.tien.springboot_traning.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
