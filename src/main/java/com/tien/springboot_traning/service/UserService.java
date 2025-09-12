package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserCreateRequestDTO userCreateRequestDTO);

    List<User> usersInformation();

    User findUserById(int userId);

    User updateUser(UserUpdateRequestDTO userUpdateRequestDTO, int userId);

    User deleteUser(int userId);
}
