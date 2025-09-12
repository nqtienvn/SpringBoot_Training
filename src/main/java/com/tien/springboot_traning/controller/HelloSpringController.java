package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.service.UserService;
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
    public User createUser(@RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        return userService.createUser(userCreateRequestDTO);
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
    public User updateUser(@RequestBody UserCreateRequestDTO userUpdateRequestDTO, @PathVariable("userId") int userId) {
        return userService.updateUser(userUpdateRequestDTO, userId);
    }
    @DeleteMapping("/users/{userId}")
    public User deleteUser(@PathVariable("userId") int userId) {
        return userService.deleteUser(userId);
    }
}
