package com.tien.springboot_traning.service;

import com.nimbusds.jose.JOSEException;
import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.request.IntrospectRequest;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse checkLogin(AuthenticationRequest authenticationRequest);
    boolean introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}

