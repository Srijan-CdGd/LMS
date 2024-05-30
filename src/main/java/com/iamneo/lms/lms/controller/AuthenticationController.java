package com.iamneo.lms.lms.controller;

import org.springframework.web.bind.annotation.RestController;

import com.iamneo.lms.lms.dto.request.LoginRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.dto.response.LoginResponse;
import com.iamneo.lms.lms.service.AuthenticationService;
import com.iamneo.lms.lms.utils.Exception.ProfileNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/lms/login")
    public ResponseEntity<BasicResponse<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        BasicResponse<LoginResponse> response = new BasicResponse<>();
        try {
            response = authenticationService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(ProfileNotFoundException e) {
            return new ResponseEntity<>(
                BasicResponse.<LoginResponse>builder()
                            .message(e.getMessage())
                            .data(null)     
                            .build(),
                HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                BasicResponse.<LoginResponse>builder()
                            .message(e.getMessage())
                            .data(null)     
                            .build(),
                HttpStatus.EXPECTATION_FAILED);
        }
    }
}
