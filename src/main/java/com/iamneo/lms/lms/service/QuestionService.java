package com.iamneo.lms.lms.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.iamneo.lms.lms.dto.response.BulkResponse;
import com.iamneo.lms.lms.model.Question;

public interface QuestionService {

    Question createQuestion(Question question, long questionBankId);

    Question updateQuestion(long questionId, Question question);

    BulkResponse<List<Question>> bulkUpload(MultipartFile file, long questionBankId) throws IOException, Exception;

    Question getQuestionById(long Id) throws Exception;

    void deleteQuestion(long questionBankId, long questionId) throws Exception;

}
