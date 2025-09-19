package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.PermissionRequest;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.PermissionResponse;
import com.tien.springboot_traning.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/permissions")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .message("tạo permission thành công")
                .result(permissionService.createPermission(permissionRequest))
                .build();
    }

    @GetMapping("/permissions")
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("lấy ra permission thành công")
                .result(permissionService.getAllPermission())
                .build();
    }

    @DeleteMapping("/permissions/{name}")
    public ApiResponse<String> delete(@PathVariable(value = "name") String name) {
        permissionService.deletePermission(name);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Xóa thành công")
                .build();
    }
}
