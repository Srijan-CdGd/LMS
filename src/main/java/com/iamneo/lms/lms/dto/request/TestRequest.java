package com.iamneo.lms.lms.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestRequest {

    // @Schema(name = "Test name", example = "CS1004/QUIZ-TEST-1", description =
    // "The name of the test you want to provide, and for clarity puposes it's
    // better to give a unique name")
    // @NotBlank(message = "Name is mandatory")
    // private String name;

    // @Schema(name = "Course Id", example = "12345", description = "From the front
    // end we don't have to ask this from the teacher, we'll give courses and ask
    // then to select using name")
    // @NotBlank(message = "Course Id is mandatory")
    // private String courseId;

    // @Schema(name = "Date of the test", example = "2024-04-01", description =
    // "Date of the test you want to create, should be after today's date or after
    // that")
    // private LocalDate date;
    // private LocalTime time;

    // @Min(value = 1, message = "Please provide the buffer time for joining (should
    // be atleast a minute)")
    // private int bufferTime;

    // @Min(value = 5, message = "Please provide a quiz duration (Should be greater
    // than or equal to 5)")
    // private int duration;

    @Schema(name = "Test name", example = "CS1004/QUIZ-TEST-1", description = "The name of the test you want to provide, and for clarity puposes it's better to give a unique name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(name = "Course Id", example = "12345", description = "From the front end we don't have to ask this from the teacher, we'll give courses and ask then to select using name")
    @NotBlank(message = "Course Id is mandatory")
    private String courseId;

    @Schema(name = "Date of the test", example = "2024-04-01", description = "Date of the test you want to create, should be after today's date or after that")
    private LocalDate date;
    private LocalTime time;

    @Min(value = 1, message = "Please provide the buffer time for joining (should be atleast a minute)")
    private int bufferTime;

    @Min(value = 5, message = "Please provide a quiz duration (Should be greater than or equal to 5)")
    private int duration;
}
