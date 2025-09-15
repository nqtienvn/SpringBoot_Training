package com.tien.springboot_traning.controller;

import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;
import com.tien.springboot_traning.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationSevice;
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        ApiResponse response = new ApiResponse();
        if(authenticationSevice.checkLogin(authenticationRequest)) {
            response.setCode(200);
            response.setMessage("Đăng nhập thành công");
            response.setResult(authenticationSevice.checkLogin(authenticationRequest));
        }
        else{
            response.setCode(500);
            response.setMessage("Đăng nhập thất bại");
            response.setResult(false);
        }
        return response;
    }
}
