package com.tien.springboot_traning.controller;

import com.nimbusds.jose.JOSEException;
import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.request.IntrospectRequest;
import com.tien.springboot_traning.dto.response.ApiResponse;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;
import com.tien.springboot_traning.dto.response.IntrospectResponse;
import com.tien.springboot_traning.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationSevice;
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationSevice.checkLogin(authenticationRequest);
        ApiResponse<AuthenticationResponse> apiResponse =  ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("đăng nhập thành công")
                .result(authenticationResponse)
                .build();
        return apiResponse;
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
       IntrospectResponse instrospectResponse = IntrospectResponse.builder()
               .valid(authenticationSevice.introspect(introspectRequest))
               .build();
        ApiResponse<IntrospectResponse> apiResponse =  ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .message("")
                .result(instrospectResponse)
                .build();
        return apiResponse;
    }
}
