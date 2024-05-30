package com.iamneo.lms.lms.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamneo.lms.lms.dto.request.RegisterRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.enumerated.Role;
import com.iamneo.lms.lms.model.User;
import com.iamneo.lms.lms.service.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/lms/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register/student")
    public ResponseEntity<BasicResponse<String>> registerStudent(@Valid @RequestBody RegisterRequest registerRequest) {
        BasicResponse<String> response = new BasicResponse<>();
        try {
            response = adminService.register(registerRequest, Role.STUDENT);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Something went wrong");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<BasicResponse<String>> registerTeacher(@Valid @RequestBody RegisterRequest registerRequest) {
        BasicResponse<String> response = new BasicResponse<>();
        try {
            response = adminService.register(registerRequest, Role.TEACHER);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Something went wrong");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAll/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
    }    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
