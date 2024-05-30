package com.iamneo.lms.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.iamneo.lms.lms.dto.request.TestRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Test;
import com.iamneo.lms.lms.service.TestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/lms/teacher/test")
@Validated
public class TestController {

    @Autowired
    private TestService testService;

    // Create a new test
    @Operation(summary = "Create a test", description = "This is the api endpoint that helps to add a test for a particular teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    @PostMapping("/addTest")
    public ResponseEntity<BasicResponse<Test>> createTest(
            @RequestBody @Parameter(name = "Test", description = "The details of the test you want to create") @Valid TestRequest testRequest) {
        try {
            Test createdTest = testService.createTest(testRequest);
            BasicResponse<Test> response = new BasicResponse<>("Test created successfully", createdTest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            BasicResponse<Test> response = new BasicResponse<>("Failed to create test: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Add questions to test
    @PostMapping("/addQuestions/{testId}")
    public ResponseEntity<BasicResponse<Test>> addQuestionsToTest(
            @PathVariable @Parameter(name = "Test Id", description = "From front end we'll get htis as soon as user selects a test", example = "12345") long testId,
            @RequestBody List<Long> questionIds) {
        try {
            Test updatedTest = testService.addQuestionsToTest(testId, questionIds);
            BasicResponse<Test> response = new BasicResponse<>("Questions added to test successfully", updatedTest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BasicResponse<Test> response = new BasicResponse<>("Failed to add questions to test: " + e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Update the test fro selected test
    @PutMapping("/updateTest/{testId}")
    public ResponseEntity<BasicResponse<Test>> updateTest(
            @Parameter(name = "Test ID", description = "The Id of the test we want to update", example = "12345") @PathVariable long testId,
            @RequestBody TestRequest testRequest) {
        try {
            Test updatedTest = testService.updateTest(testId, testRequest);
            BasicResponse<Test> response = new BasicResponse<>("Test updated successfully", updatedTest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BasicResponse<Test> response = new BasicResponse<>("Failed to update test: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getResults/test/{testId}")
    public ResponseEntity<BasicResponse<List<Answer>>> getMethodName(@PathVariable long testId) {
        try {
            return new ResponseEntity<>(BasicResponse.<List<Answer>>builder().data(testService.getTestResults(testId))
                    .message("Users fetched successfully").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    BasicResponse.<List<Answer>>builder().data(null)
                            .message("Error fetching the results: " + e.getMessage()).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/testId/{testId}")
    public ResponseEntity<String> deleteTest(@PathVariable long testId) {
        testService.deleteTest(testId);
        return new ResponseEntity<>("Test Deleted successfully", HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

}
