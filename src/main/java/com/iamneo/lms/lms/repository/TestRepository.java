package com.iamneo.lms.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iamneo.lms.lms.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findAllByCourseId(String courseId);

}
