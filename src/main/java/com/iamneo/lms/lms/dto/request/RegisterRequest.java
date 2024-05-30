package com.iamneo.lms.lms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    // private String userId;
    // private String name;

    // @Size(min = 8, message = "Password length should be atleast 8")
    // private String password;

    @NotNull(message = "User ID cannot be null")
    @Schema(name = "User ID", example = "123456", description = "The unique identifier for the user")
    private String userId;

    @NotNull(message = "Name cannot be null")
    @Schema(name = "Name", example = "John Doe", description = "The name of the user")
    private String name;

    @Size(min = 8, message = "Password length should be at least 8")
    @Schema(name = "Password", example = "admin@1", description = "The password for the user")
    private String password;
}
