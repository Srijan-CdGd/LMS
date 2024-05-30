package com.iamneo.lms.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long choiceId;

    private String optionBody;
    private boolean isCorrect;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    private Question question;
}
