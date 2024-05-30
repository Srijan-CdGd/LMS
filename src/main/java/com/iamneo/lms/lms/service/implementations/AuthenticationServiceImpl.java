package com.iamneo.lms.lms.service.implementations;

// public class AuthenticationServiceImpl {
    
// }


// package com.iamneo.springboot.LMS.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.iamneo.lms.lms.dto.request.LoginRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.dto.response.LoginResponse;
import com.iamneo.lms.lms.model.Token;
import com.iamneo.lms.lms.model.User;
import com.iamneo.lms.lms.repository.TokenRepository;
import com.iamneo.lms.lms.repository.UserRepository;
import com.iamneo.lms.lms.service.AuthenticationService;
import com.iamneo.lms.lms.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    @Override
    public BasicResponse<LoginResponse> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        var userExists = userRepository.findByUserId(loginRequest.getUserId()).orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        System.out.println("\n\n\n" + userExists.getRole().toString() + "\n\n\n");
        claims.put("role", userExists.getRole().toString());
        String accessToken = jwtUtil.generateToken(userExists, claims);
        revokeAllUserTokens(userExists);
        saveUserToken(accessToken, userExists);
        return BasicResponse.<LoginResponse>builder()
                .message("User logged in successfully")
                .data(
                        LoginResponse.builder().accessToken(accessToken).id(userExists.getUserId()).role(userExists.getRole())
                                .build())
                .build();
    }

    private void saveUserToken(String accessToken, User user) {
        var token = Token.builder()
                .token(accessToken)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_IdAndRevokedFalseAndExpiredFalse(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}