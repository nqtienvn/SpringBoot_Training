package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.UserResponse;
import com.tien.springboot_traning.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
     UserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello spring boot";
    }

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO) throws Exception {
            ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
            apiResponse.setCode(200);
            apiResponse.setMessage("success");
            apiResponse.setResult(userService.createUser(userCreateRequestDTO));
            return apiResponse;
    }
    @GetMapping("/users")
    public List<UserResponse> UsersInformation() {
        return userService.usersInformation();
    }
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponse> findById(@PathVariable("userId") int userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("success");
        apiResponse.setResult(userService.findUserById(userId));
        return apiResponse;
    }
    //vào trang cá nhân sau khi đăng nhập
    @GetMapping("/users/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("success");
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

    @PutMapping("/users/{userId}")
    public ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO, @PathVariable("userId") int userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("success");
        apiResponse.setResult(userService.updateUser(userUpdateRequestDTO, userId));
        return apiResponse;
    }
    @DeleteMapping("/users/{userId}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable("userId") int userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("success");
        apiResponse.setResult(userService.deleteUser(userId));
        return apiResponse;
    }
}
