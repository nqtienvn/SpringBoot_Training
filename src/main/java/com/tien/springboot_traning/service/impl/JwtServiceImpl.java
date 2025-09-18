package com.tien.springboot_traning.service.impl;

import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.request.IntrospectRequest;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.entity.jwt.JwtKeyUntil;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService {
    UserRepository userRepository;
    JwtKeyUntil jwtKeyUntil;

    @Override
    public AuthenticationResponse checkLogin(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findUserByName(authenticationRequest.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.PASS_INCORECT);
        }
            var token = generateToken(authenticationRequest.getName());
            return AuthenticationResponse.builder()
                    .isAuthenticated(true)
                    .token(token)
                    .build();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + jwtKeyUntil.getExpiration()))
                .signWith(jwtKeyUntil.getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    @Override
    public Boolean verifyToken(IntrospectRequest introspectRequest) {
        try{
            Jwts.parser() //chuẩn bị một cái máy để đọc(máy quét)
                    .setSigningKey(jwtKeyUntil.getSignKey()) //cung cấp sercret key để parser
                    .build() //tạo JWT parser haonf chỉnh để JWT phân tích
                    //làm tất cả
                    //táck token thành header.payload.signature
                    //tạo signed mới
                    //so sánh với signed mới
                    .parse(introspectRequest.getToken()); // k hợp lệ sẽ ném exception ngay
            return true;
        }catch(Exception ex) {
            throw new AppException(ErrorCode.ERROR_TOKEN);
        }
    }
}
