package com.tien.springboot_traning.service;

import com.tien.springboot_traning.dto.request.AuthenticationRequest;

public interface AuthenticationService {
    boolean checkLogin(AuthenticationRequest authenticationRequest);
}
