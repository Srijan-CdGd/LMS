package com.iamneo.lms.lms.service;

import java.util.List;

import com.iamneo.lms.lms.dto.request.AnswerRequest;
import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Test;

public interface StudentService {

    List<Test> getAllTests();

    Test getTestById(long testId);

    List<Test> getAllTestByCourseId(String courseId);

    Answer setAnswer(AnswerRequest answerRequest);

}
