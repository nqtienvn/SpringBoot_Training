package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponse;
import com.tien.springboot_traning.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequestDTO userCreateRequestDTO) throws Exception;

    List<UserResponse> usersInformation();

    UserResponse findUserById(int userId);

    UserResponse updateUser(UserUpdateRequestDTO userUpdateRequestDTO, int userId);

    UserResponse deleteUser(int userId);
    UserResponse getMyInfo();
}
