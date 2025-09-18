package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponse;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.enums.Roles;
import com.tien.springboot_traning.mapper.UserMapper;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
     UserRepository userRepository;
     UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreateRequestDTO userCreateRequestDTO) {
        if (userRepository.existsByName(userCreateRequestDTO.getName())) {
            throw new RuntimeException("user name existed");
        }
        User user = userMapper.toUser(userCreateRequestDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequestDTO.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> usersInformation() {
        var authorization =  SecurityContextHolder.getContext().getAuthentication();
        log.info(authorization.getName());
        //granted là kết quả của cơ chế của Oauth2 resouce server map từ jwt sang security để lấy về scope
        authorization.getAuthorities().forEach(granted -> log.info(granted.getAuthority()));
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> {userResponses.add(userMapper.toUserResponse(user));});
        return userResponses;
    }

    @Override
    public UserResponse findUserById(int userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponse updateUser(UserUpdateRequestDTO userUpdateRequestDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (userRepository.existsByName(userUpdateRequestDTO.getName())) {
            throw new RuntimeException("user name existed");
        }
        userMapper.toUserUpdate(userUpdateRequestDTO, user);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }
}
