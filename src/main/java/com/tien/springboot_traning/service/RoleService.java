package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.RoleRequest;
import com.tien.springboot_traning.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    List<RoleResponse> listRole();
    void deleteRole(String name);
}
