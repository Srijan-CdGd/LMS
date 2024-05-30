package com.iamneo.lms.lms.service;

import java.util.List;

import com.iamneo.lms.lms.dto.request.RegisterRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.enumerated.Role;
import com.iamneo.lms.lms.model.User;


public interface AdminService {

    BasicResponse<String> register(RegisterRequest registerRequest, Role role) throws Exception;

    List<User> findAll();
    
}
