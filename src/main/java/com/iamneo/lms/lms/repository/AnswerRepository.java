package com.iamneo.lms.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamneo.lms.lms.model.Answer;
import com.iamneo.lms.lms.model.Test;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByTest(Test test);

}
