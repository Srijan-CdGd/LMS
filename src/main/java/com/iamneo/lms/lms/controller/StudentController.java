package com.iamneo.lms.lms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iamneo.lms.lms.dto.request.AnswerRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Test;
import com.iamneo.lms.lms.service.StudentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/lms/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/answer")
    public ResponseEntity<BasicResponse<Answer>> studentAnswer(@RequestBody AnswerRequest answerRequest) {
        try {
            Answer answer = studentService.setAnswer(answerRequest);
            return new ResponseEntity<>(
                    BasicResponse.<Answer>builder().data(answer).message("Answer saved successfully").build(),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(BasicResponse.<Answer>builder().data(null)
                    .message("Failed to save the answer: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get All tests that are there
    @GetMapping("/getAllTests")
    public ResponseEntity<BasicResponse<List<Test>>> getAllTests() {
        try {
            List<Test> tests = studentService.getAllTests();
            BasicResponse<List<Test>> response = new BasicResponse<>("All tests fetched successfully", tests);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BasicResponse<List<Test>> response = new BasicResponse<>("Failed to fetch tests: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get Test with test id
    @GetMapping("/getSpeceficTest/{testId}")
    public ResponseEntity<BasicResponse<Test>> getTestById(@PathVariable long testId) {
        try {
            Test test = studentService.getTestById(testId);
            if (test != null) {
                BasicResponse<Test> response = new BasicResponse<>("Test fetched successfully", test);
                return ResponseEntity.ok(response);
            } else {
                BasicResponse<Test> response = new BasicResponse<>("Test not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            BasicResponse<Test> response = new BasicResponse<>("Failed to fetch test: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }    
}
