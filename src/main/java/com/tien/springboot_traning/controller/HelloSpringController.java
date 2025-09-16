package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.UserResponseDTO;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloSpringController {
    @Autowired
    private UserService userService;
    @GetMapping("/hello")
    public String sayHello() {
        return "hello spring boot";
    }

    @PostMapping("/users")
    public ApiResponse<UserResponseDTO> createUser(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Tạo thành công user");
        apiResponse.setCode(200);
        apiResponse.setResult(userService.createUser(userCreateRequestDTO));
        return apiResponse;
    }
    @GetMapping("/users")
    public ApiResponse<List<UserResponseDTO>> UsersInformation() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Lấy thành công list users");
        apiResponse.setCode(200);
        apiResponse.setResult(userService.usersInformation());
        return apiResponse;
    }
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponseDTO> findById(@PathVariable("userId") int userId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Tìm thành công user");
        apiResponse.setCode(200);
        apiResponse.setResult(userService.findUserById(userId));
        return apiResponse;
    }
    @PutMapping("/users/{userId}")
    public ApiResponse<UserResponseDTO> updateUser(@RequestBody UserCreateRequestDTO userUpdateRequestDTO, @PathVariable("userId") int userId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Cập nhật thành công user");
        apiResponse.setCode(200);
        apiResponse.setResult(userService.updateUser(userUpdateRequestDTO, userId));
        return apiResponse;
    }
    @DeleteMapping("/users/{userId}")
    public ApiResponse<UserResponseDTO> deleteUser(@PathVariable("userId") int userId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Xóa thành công user");
        apiResponse.setCode(200);
        apiResponse.setResult(userService.deleteUser(userId));
        return apiResponse;
    }
}
