package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.request.IntrospectRequest;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;

public interface JwtService {
    AuthenticationResponse checkLogin(AuthenticationRequest authenticationRequest);

    Boolean verifyToken(IntrospectRequest introspectRequest);
}
