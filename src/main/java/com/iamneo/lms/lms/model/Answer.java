package com.iamneo.lms.lms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    private String studentId;
    private float marks;

    @JsonBackReference
    @OneToOne
    private Question question;

    @JsonBackReference
    @OneToMany
    private List<Choice> answers;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "test", referencedColumnName = "testId")
    private Test test;
}
