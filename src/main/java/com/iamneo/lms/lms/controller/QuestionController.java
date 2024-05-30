package com.iamneo.lms.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.iamneo.lms.lms.dto.request.QuestionRequest;
import com.iamneo.lms.lms.dto.response.BasicResponse;
import com.iamneo.lms.lms.model.Question;
import com.iamneo.lms.lms.service.QuestionService;
import com.iamneo.lms.lms.utils.ServiceUtil;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/lms/teacher/questions")
@Validated
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final ServiceUtil serviceUtil;

    // Create a new question with choices
    @PostMapping("/question-bank/{questionBankId}")
    public ResponseEntity<BasicResponse<Question>> createQuestionWithChoices(@PathVariable long questionBankId,
            @RequestBody @Valid QuestionRequest questionRequest) {
        try {
            Question question = serviceUtil.mapRequestToEntity(questionRequest, questionBankId);
            Question createdQuestion = questionService.createQuestion(question, questionBankId);

            return ResponseEntity.status(HttpStatus.CREATED).body(BasicResponse.<Question>builder()
                    .data(createdQuestion).message("Question Created successfully").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BasicResponse.<Question>builder().data(null).message(e.getMessage()).build());
        }
    }

    // Update a question with choices
    @PutMapping("/{questionId}/question-bank/{questionBankId}")
    public ResponseEntity<BasicResponse<Question>> updateQuestionWithChoices(@PathVariable long questionId,
            @PathVariable long questionBankId, @RequestBody @Valid QuestionRequest questionRequest) {
        try {
            Question question = serviceUtil.mapRequestToEntity(questionRequest, questionBankId);
            Question updatedQuestion = questionService.updateQuestion(questionId, question);
            return ResponseEntity.ok(BasicResponse.<Question>builder().data(updatedQuestion)
                    .message("Question updated successully").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BasicResponse.<Question>builder().data(null).message(e.getMessage()).build());
        }
    }

    @SuppressWarnings("null")
    @PostMapping(value = "/bulkQuestionUpload/{questionBankId}", consumes = "multipart/form-data")
    public ResponseEntity<BasicResponse<?>> uploadQuestionBulk(@RequestParam("file") MultipartFile file,
            @PathVariable long questionBankId) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.<List<Question>>builder().data(null)
                    .message("file is empty, please check once again and upload file").build());
        }

        if (file.getContentType().equals("test/csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.<List<Question>>builder().data(null)
                    .message("Please upload a CSV file").build());
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    BasicResponse.<Object>builder()
                            .data(questionService.bulkUpload(file, questionBankId)).message("OK")
                            .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BasicResponse.<String>builder().message(e.getMessage()).data(null).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BasicResponse.<String>builder().message(e.getMessage()).data(null).build());
        }

    }

    @DeleteMapping("/questionBank/{questionBankId}/question/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable long questionBankId, @PathVariable long questionId) {
        try {
            questionService.deleteQuestion(questionBankId, questionId);
            return new ResponseEntity<>("Question Deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Question deletion unsuccessfull" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Exception handler for method argument validation errors
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
