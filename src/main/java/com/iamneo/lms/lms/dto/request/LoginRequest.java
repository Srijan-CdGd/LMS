package com.iamneo.lms.lms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "User ID cannot be blank")
    @Schema(description = "User ID", example = "john.doe", required = true)
    private String userId;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password", example = "admin1", required = true)
    private String password;
}
