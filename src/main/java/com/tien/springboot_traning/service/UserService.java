package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponseDTO;
import com.tien.springboot_traning.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO);

    List<UserResponseDTO> usersInformation();

    UserResponseDTO findUserById(int userId);

    UserResponseDTO updateUser(UserCreateRequestDTO userUpdateRequestDTO, int userId);

    UserResponseDTO deleteUser(int userId);
}
