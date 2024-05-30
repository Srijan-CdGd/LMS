package com.iamneo.lms.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamneo.lms.lms.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
