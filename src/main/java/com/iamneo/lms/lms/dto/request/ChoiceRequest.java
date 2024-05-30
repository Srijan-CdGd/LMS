package com.iamneo.lms.lms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceRequest {
    
    @NotBlank(message = "Please provide an option")
    @Schema(name = "Option Body", example = "Paris", description = "The body of the option", required = true)
    private String optionBody;

    @NotNull(message = "Provide if this option is correct or not")
    @Schema(name = "Is Correct", example = "true", description = "Indicates whether this option is correct or not", required = true)
    private boolean isCorrect;
}
