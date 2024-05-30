package com.iamneo.lms.lms.service.implementations;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.iamneo.lms.lms.dto.request.TestRequest;
import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Question;
import com.iamneo.lms.lms.model.Test;
import com.iamneo.lms.lms.repository.AnswerRepository;
import com.iamneo.lms.lms.repository.QuestionRepository;
import com.iamneo.lms.lms.repository.TestRepository;
import com.iamneo.lms.lms.service.TestService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Test createTest(TestRequest testRequest) {
        try {
            if (testRequest.getDate().atTime(testRequest.getTime()).isBefore(LocalDateTime.now())) {
                throw new Exception("The date and time should be after the current date");
            }
            Test test = Test.builder()
                    .name(testRequest.getName())
                    .courseId(testRequest.getCourseId())
                    .bufferTime(testRequest.getBufferTime())
                    .duration(testRequest.getDuration())
                    .startDateTime(testRequest.getDate().atTime(testRequest.getTime()))
                    .build();
            Test savedTest = testRepository.save(test);
            return savedTest;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Test updateTest(long testId, TestRequest testRequest) {
        try {
            Test test = testRepository.findById(testId).orElseThrow(() -> new Exception("Test not found"));
            if (!test.getCourseId().equals(testRequest.getCourseId())) {
                throw new Exception("Course cannot be updated");
            }
            if (testRequest.getDate().atTime(testRequest.getTime()).isBefore(LocalDateTime.now())) {
                throw new Exception("The date and time should be after the current date");
            }
            test.setName(testRequest.getName());
            test.setCourseId(testRequest.getCourseId());
            test.setBufferTime(testRequest.getBufferTime());
            test.setDuration(testRequest.getDuration());
            test.setStartDateTime(testRequest.getDate().atTime(testRequest.getTime()));
            Test updatedTest = testRepository.save(test);
            return updatedTest;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Test addQuestionsToTest(long testId, List<Long> questionId) {
        try {
            Test test = testRepository.findById(testId).orElseThrow(() -> new Exception("Test not found"));
            for (Long id : questionId) {
                Question question = questionRepository.findById(id)
                        .orElseThrow(() -> new Exception("Question with id " + id + "not found"));
                // question.setTest(test);
                // question = questionRepository.save(question);
                test.getQuestions().add(question);
            }
            Test updatedTest = testRepository.save(test);
            return updatedTest;
        } catch (Exception e) {
            throw new ServiceException("Failed to add questions to test: " + e.getMessage());
        }
    }

    @Override
    public List<Answer> getTestResults(long testId) throws Exception {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new Exception("Test with the id " + testId + "Not found"));
        return answerRepository.findAllByTest(test);
    }

    @Override
    public void deleteTest(long testId) {
        testRepository.deleteById(testId);
    }
}