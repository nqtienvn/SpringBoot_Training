package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserCreateRequestDTO userCreateRequestDTO) {
        User user = new User();
        user.setName(userCreateRequestDTO.getName());
        user.setAge(userCreateRequestDTO.getAge());
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
    public User updateUser(UserCreateRequestDTO userUpdateRequestDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setName(userUpdateRequestDTO.getName());
            user.setAge(userUpdateRequestDTO.getAge());
            return userRepository.save(user);
    }

    @Override
    public User deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
         userRepository.delete(user);
         return user;
    }
}
