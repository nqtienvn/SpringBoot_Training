package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.mapper.UserMapper;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public User createUser(UserCreateRequestDTO userCreateRequestDTO) {
        if (userRepository.existsByName(userCreateRequestDTO.getName())) {
            throw new RuntimeException("user name existed");
        }
        User user = userMapper.toUser(userCreateRequestDTO);
        return userRepository.save(user);
    }

    @Override
    public List<User> usersInformation() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(UserUpdateRequestDTO userUpdateRequestDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (userRepository.existsByName(userUpdateRequestDTO.getName())) {
            throw new RuntimeException("user name existed");
        }
        userMapper.toUserUpdate(userUpdateRequestDTO, user);
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return user;
    }
}
