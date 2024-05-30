package com.iamneo.lms.lms.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.iamneo.lms.lms.dto.request.AnswerRequest;
import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Choice;
import com.iamneo.lms.lms.model.Question;
import com.iamneo.lms.lms.model.Test;
import com.iamneo.lms.lms.repository.AnswerRepository;
import com.iamneo.lms.lms.repository.QuestionRepository;
import com.iamneo.lms.lms.repository.TestRepository;
import com.iamneo.lms.lms.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Test getTestById(long testId) {
        try {
            Optional<Test> optionalTest = testRepository.findById(testId);
            return optionalTest.orElse(null);
        } catch (Exception e) {
            throw new ServiceException("Failed to fetch test: " + e.getMessage());
        }
    }

    @Override
    public List<Test> getAllTests() {
        try {
            return testRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to fetch tests: " + e.getMessage());
        }
    }

    @Override
    public List<Test> getAllTestByCourseId(String courseId) {
        try {
            return testRepository.findAllByCourseId(courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Answer setAnswer(AnswerRequest answerRequest) {
        Answer answer = mapRequestToAnswer(answerRequest);
        return answerRepository.save(answer);
    }

    private Answer mapRequestToAnswer(AnswerRequest answerRequest) {
        Question question = questionRepository.findById(answerRequest.getQuestionId())
                .orElseThrow(() -> new ServiceException("The question does not exists"));
        Test test = testRepository.findById(answerRequest.getTestId())
                .orElseThrow(() -> new ServiceException("The test does not exists"));
        List<Choice> choices = new ArrayList<>();

        answerRequest.getAnswers().forEach(choiceId -> {
            for (int ind = 0; ind < question.getOptions().size(); ind++) {
                if (question.getOptions().get(ind).getChoiceId() == choiceId) {
                    choices.add(question.getOptions().get(ind));
                }
            }
        });

        // answerRequest.getAnswers().stream().
        // answerRequest.getAnswers().stream()
        // .forEach(x -> {
        // question.getOptions().stream().(y -> x==y.getChoiceId());
        // })
        // .collect(Collectors.toList());

        long marks = choices.stream().filter(x -> x.isCorrect()).count();

        return Answer.builder()
                .question(question)
                .answers(choices)
                .studentId(answerRequest.getStudentId())
                .marks(marks / question.getNumCorrectAnswers())
                .test(test)
                .build();
    }
}
