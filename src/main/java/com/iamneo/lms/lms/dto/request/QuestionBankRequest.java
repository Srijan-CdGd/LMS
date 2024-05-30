package com.iamneo.lms.lms.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionBankRequest {
    @NotBlank(message = "Name is mandatory")
    @Schema(name = "Name", description = "The name of the course", example = "Introduction to Programming")
    private String name;

    @NotBlank(message = "Teacher ID is required")
    @Schema(name = "Teacher ID", description = "The ID of the teacher associated with the course", example = "12345")
    private String teacherId;

    @Schema(name = "Tags", description = "List of tags associated with the course", example = "[\"Programming\", \"Java\"]")
    private List<String> tags;
}
