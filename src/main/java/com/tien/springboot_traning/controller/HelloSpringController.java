package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.entity.User;
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
public class HelloSpringController {
     UserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return "hello spring boot";
    }

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO) {
            ApiResponse<User> apiResponse = new ApiResponse<>();
            apiResponse.setResult(userService.createUser(userCreateRequestDTO));
            return apiResponse;
    }
    @GetMapping("/users")
    public List<User> UsersInformation() {
        return userService.usersInformation();
    }
    @GetMapping("/users/{userId}")
    public User findById(@PathVariable("userId") int userId) {
        return userService.findUserById(userId);
    }
    @PutMapping("/users/{userId}")
    public User updateUser(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO, @PathVariable("userId") int userId) {
        return userService.updateUser(userUpdateRequestDTO, userId);
    }
    @DeleteMapping("/users/{userId}")
    public User deleteUser(@PathVariable("userId") int userId) {
        return userService.deleteUser(userId);
    }
}
