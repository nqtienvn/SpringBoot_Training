package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.RoleRequest;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.RoleResponse;
import com.tien.springboot_traning.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;
    @PostMapping("/roles")
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("tạo role thành công")
                .result(roleService.createRole(roleRequest))
                .build();
    }

    @GetMapping("/roles")
    public ApiResponse<List<RoleResponse>> getAllRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .message("tạo role thành công")
                .result(roleService.listRole())
                .build();
    }

    @DeleteMapping("/roles/{name}")
    public ApiResponse<String> delete(@PathVariable(value = "name") String name) {
        roleService.deleteRole(name);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("xóa role thành công")
                .build();
    }
}
