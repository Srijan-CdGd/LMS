package com.iamneo.lms.lms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @Pattern(regexp = "^[\\d]{10}", message = "Please provide a correct phone num")
    private String phoneNum;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Schema(name = "User Email", example = "example@gmail.com", description = "User email, better to have organistation provided email")
    private String email;

    @NotNull(message = "Please provide a address")
    private AddressRequest address;
}
