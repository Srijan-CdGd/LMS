package com.iamneo.lms.lms.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long testId;

    private String name;
    private String courseId;
    private LocalDateTime startDateTime;
    private int bufferTime;
    private int duration;

    // @JsonBackReference
    @ManyToMany
    @JoinTable(
        name = "questions_test",
        joinColumns = @JoinColumn(name = "test_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id_ans")
    )
    private Set<Question> questions;

    // @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY)
    private List<Answer> answer;
}
