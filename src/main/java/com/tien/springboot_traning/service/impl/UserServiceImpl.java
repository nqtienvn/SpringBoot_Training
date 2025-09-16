package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponseDTO;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
        User user = new User();
        if (userRepository.existsByName(userCreateRequestDTO.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setName(userCreateRequestDTO.getName());
        user.setAge(userCreateRequestDTO.getAge());
        userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setAge(user.getAge());
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> usersInformation() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        if(users.isEmpty()) {
            throw  new AppException(ErrorCode.USER_EMPTY);
        }
        else {
            users.forEach(user -> { UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setId(user.getId());
                userResponseDTO.setName(user.getName());
                userResponseDTO.setAge(user.getAge());
                userResponseDTOS.add(userResponseDTO);
            });
        }
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO findUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setAge(user.getAge());
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO updateUser(UserCreateRequestDTO userUpdateRequestDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setName(userUpdateRequestDTO.getName());
        user.setAge(userUpdateRequestDTO.getAge());
        userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setAge(user.getAge());
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setAge(user.getAge());
        return userResponseDTO;
    }
}
