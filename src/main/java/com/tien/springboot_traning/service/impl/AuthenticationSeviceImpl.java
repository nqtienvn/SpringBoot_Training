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
import com.tien.springboot_traning.entity.jwt.JwtProperties;
import com.tien.springboot_traning.exception.AppException;
import com.tien.springboot_traning.exception.ErrorCode;
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
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationSeviceImpl implements AuthenticationService {
    UserRepository userRepository;
    JwtProperties jwtProperties;
    @Override
    public AuthenticationResponse checkLogin(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findUserByName(authenticationRequest.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXTSTED));
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.PASS_INCORECT);
        } else {
            var token = generateToken(user);
            return AuthenticationResponse.builder()
                    .isAuthenticated(true)
                    .token(token)
                    .build();
        }
    }

    @Override
    public boolean introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        //signedJWT sẽ parse lại token thành kiểu object có các thuộc tính như là: header, payload, signnature
        SignedJWT signedJWT = SignedJWT.parse(token); //trả về kiểu object để dễ thao tác

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        //đây là khời tạo đối tượng với key
        JWSVerifier verifier = new MACVerifier(jwtProperties.getSecret().getBytes());
        //cái veryfied này được thực hiện như sau
        //ở đối tượng signedJWT nó sẽ lấy được về 3 thuộc tính và sẽ tính lại token bằng header, pay load , key trong verifier
        //và nó sẽ so sánh luôn với cái signed cũ trong signedJWT và trả về true hoặc false
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                        .build().isValid();
    }

    public String generateToken(User user) {
        // headaer
        //nợi định nghĩa gửi về loại token gì
        //nơi định nghĩa thuật toán để tạo ra signature, k phỉa thuật toán tọa token(header.payload.signature)
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Payload
        //data trong token
        JWTClaimsSet jwsClaimSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .issuer("tien.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                //custom claim
                //role là set nên là ta cần chuyển set này về String
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwsClaimSet.toJSONObject());

       //tạo token
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(jwtProperties.getSecret().getBytes()));

            //cần một salt 32 bytes
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN);
        }
    }
    public String buildScope(User user) {
        //joiner string bằng dấu cách thay cho concat thủ công hay add bằng String builder
        StringJoiner joiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role ->  joiner.add(role.getName()));
            return joiner.toString();
        }
        return null;
    }
}
