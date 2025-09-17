package com.tien.springboot_traning.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tien.springboot_traning.dto.request.AuthenticationRequest;
import com.tien.springboot_traning.dto.request.IntrospectRequest;
import com.tien.springboot_traning.dto.response.AuthenticationResponse;
import com.tien.springboot_traning.dto.response.IntrospectResponse;
import com.tien.springboot_traning.entity.User;
import com.tien.springboot_traning.repository.UserRepository;
import com.tien.springboot_traning.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationSeviceImpl implements AuthenticationService {
    UserRepository userRepository;
    @NonFinal
        protected static final String SIGN_KEY = "6Ta61TEI2W5QeAuXkCfeKgzwQpfc1ySysqd0xH6Ey6W7eofgJ97pSsUqeocaqlmD";//tránh inject vào constructor
    @Override
    public AuthenticationResponse checkLogin(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findUserByName(authenticationRequest.getName()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản cần đăng nhập"));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (authenticated == false) {
            throw new RuntimeException("Đăng nhập không thành công");
        } else {
            var token = generateToken(authenticationRequest.getName());
            AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                    .isAuthenticated(true)
                    .token(token)
                    .build();
            return authenticationResponse;
        }
    }

    @Override
    public boolean introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        //signedJWT sẽ parse lại token thành kiểu object có các thuộc tính như là: header, payload, signnature
        SignedJWT signedJWT = SignedJWT.parse(token); //trả về kiểu object để dễ thao tác

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        //đây là khời tạo đối tượng với key
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        //cái veryfied này được thực hiện như sau
        //ở đối tượng signedJWT nó sẽ lấy được về 3 thuộc tính và sẽ tính lại token bằng header, pay load , key trong verifier
        //và nó sẽ so sánh luôn với cái signed cũ trong signedJWT và trả về true hoặc false
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                        .build().isValid();
    }

    public String generateToken(String name) {
        // headaer
        //nợi định nghĩa gửi về loại token gì
        //nơi định nghĩa thuật toán để tạo ra signature, k phỉa thuật toán tọa token(header.payload.signature)
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Payload
        //data trong token
        JWTClaimsSet jwsClaimSet = new JWTClaimsSet.Builder()
                .subject(name)
                .issuer("tien.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                //custom claim
                .claim("role", "[admin, user]")
                .build();
        Payload payload = new Payload(jwsClaimSet.toJSONObject());

       //tạo token
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes(StandardCharsets.UTF_8)));

            //cần một salt 32 bytes
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot create token");
        }
    }
}
