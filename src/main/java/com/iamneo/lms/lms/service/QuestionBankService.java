package com.iamneo.lms.lms.service;

import java.util.List;

import com.iamneo.lms.lms.model.QuestionBank;

public interface QuestionBankService {

    QuestionBank createQuestionBank(QuestionBank questionBank);

    List<QuestionBank> getAllQuestionBanks();

    QuestionBank findById(long questionBankId) throws Exception;

    public QuestionBank updateQuestionBank(long id, QuestionBank updatedQuestionBank);

    void deleteQuestionBank(long questionBankId);

}
