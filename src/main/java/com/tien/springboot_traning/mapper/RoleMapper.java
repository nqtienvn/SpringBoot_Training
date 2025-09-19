package com.tien.springboot_traning.mapper;

import com.tien.springboot_traning.dto.request.RoleRequest;
import com.tien.springboot_traning.dto.response.RoleResponse;
import com.tien.springboot_traning.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
