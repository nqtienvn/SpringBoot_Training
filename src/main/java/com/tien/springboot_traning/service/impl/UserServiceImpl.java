package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.UserCreateRequestDTO;
import com.tien.springboot_traning.dto.request.UserUpdateRequestDTO;
import com.tien.springboot_traning.dto.response.UserResponse;
import com.tien.springboot_traning.entity.Role;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.enums.Roles;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
import com.tien.springboot_traning.mapper.UserMapper;
import com.tien.springboot_traning.repository.RoleRepository;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
     RoleRepository roleRepository;
    @Override
    public UserResponse createUser(UserCreateRequestDTO userCreateRequestDTO) throws Exception {
        if (userRepository.existsByName(userCreateRequestDTO.getName())) {
            throw new AppException(ErrorCode.USER_NOT_EXTSTED);
        }
        User user = userMapper.toUser(userCreateRequestDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequestDTO.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        if(roleRepository.findById(Roles.USER.name()).isEmpty()) {
            roles.add(roleRepository.save(Role.builder().name(Roles.USER.name()).build()));
        }
        else {
            roles.add(roleRepository.findById(Roles.USER.name()).orElseThrow(Exception::new));
        }
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    //chạy nếu là admin
    public List<UserResponse> usersInformation() {
        var authorization =  SecurityContextHolder.getContext().getAuthentication();
        log.info(authorization.getName());
        log.info("chạy");
        //granted là kết quả của cơ chế của Oauth2 resouce server map từ jwt sang security để lấy về scope
        authorization.getAuthorities().forEach(granted -> log.info(granted.getAuthority()));
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> {userResponses.add(userMapper.toUserResponse(user));});
        return userResponses;
    }

    @Override
    //id của chính mình thì mình mới được lấy nếu không phải là ad min
    //còn nếu là admin thì được lấy tất
    @PostAuthorize("returnObject.name == authentication.name or hasRole('ADMIN')")
    public UserResponse findUserById(int userId) {
        log.info("chạy");
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED)));
    }

    @Override
    @PostAuthorize("returnObject.name == authentication.name")
    //chir co no moi duoc update no
    public UserResponse updateUser(UserUpdateRequestDTO userUpdateRequestDTO, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED));
        if (userRepository.existsByName(userUpdateRequestDTO.getName())) {
            throw new AppException(ErrorCode.NAME_EXIST);
        }
        userMapper.toUserUpdate(userUpdateRequestDTO, user);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findUserByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED));
        return userMapper.toUserResponse(user);
    }
}
