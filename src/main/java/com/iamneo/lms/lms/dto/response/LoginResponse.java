package com.iamneo.lms.lms.dto.response;

import com.iamneo.lms.lms.enumerated.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    public String id;
    public String accessToken;

    public Role role;
}
