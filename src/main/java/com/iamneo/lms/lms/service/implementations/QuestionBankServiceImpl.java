package com.iamneo.lms.lms.service.implementations;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.iamneo.lms.lms.model.QuestionBank;
import com.iamneo.lms.lms.repository.QuestionBankRepository;
import com.iamneo.lms.lms.service.QuestionBankService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionBankServiceImpl implements QuestionBankService {

    private final QuestionBankRepository questionBankRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuestionBankServiceImpl.class);

    public QuestionBank createQuestionBank(QuestionBank questionBank) {
        try {
            // QuestionBank questionBank = serviceUtil.mapRequestToEntity(questionBankRequest);
            return questionBankRepository.save(questionBank);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating the question bank: " + e.getMessage());
            throw new ServiceException(
                    "An unexpected error occurred while creating the question bank: " + e.getMessage());
        }
    }

    public List<QuestionBank> getAllQuestionBanks() {
        return questionBankRepository.findAll();
    }

    public QuestionBank getQuestionBankById(long id) {
        return questionBankRepository.findById(id).orElse(null);
    }

    public void deleteQuestionBank(long id) {
        questionBankRepository.deleteById(id);
    }

    @Override
    public QuestionBank findById(long questionBankId) throws Exception {
        return questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new Exception("Question Bank not found"));
    }

    @Override
    public QuestionBank updateQuestionBank(long id, QuestionBank updatedQuestionBank) {
        try {
            // Retrieve the existing question bank
            // QuestionBank updatedQuestionBank = serviceUtil.mapRequestToEntity(QuestionBankRequest);
            QuestionBank existingQuestionBank = questionBankRepository.findById(id)
                    .orElseThrow(() -> new Exception("Question bank not found with id: " + id));

            if (!existingQuestionBank.getTeacherId().equals(updatedQuestionBank.getTeacherId())) {
                throw new Exception("The teacher Id cannot be updated");
            }

            // Update the fields of the existing question bank
            existingQuestionBank.setName(updatedQuestionBank.getName());
            existingQuestionBank.setTeacherId(updatedQuestionBank.getTeacherId());
            existingQuestionBank.setTags(updatedQuestionBank.getTags());

            // Save the updated question bank
            return questionBankRepository.save(existingQuestionBank);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while updating the question bank: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }
}
