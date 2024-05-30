package com.iamneo.lms.lms.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    private String question;

    private short numCorrectAnswers;

    // @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Choice> options;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private QuestionBank questionBank;

    @JsonBackReference
    @ManyToMany
    // @JoinColumn(name = "test_id", referencedColumnName = "testId")
    private Set<Test> tests;
}
