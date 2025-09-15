package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationSeviceImpl implements AuthenticationService {
    UserRepository userRepository;
    @Override
    public boolean checkLogin(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            User user = userRepository.findUserByName(authenticationRequest.getName()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản cần đăng nhập"));
            return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
    }
}
