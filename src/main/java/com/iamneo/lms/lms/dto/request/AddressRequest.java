package com.iamneo.lms.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressRequest {
    
    @NotBlank(message = "Please provide the house number")
    private String houseNum;

    @NotBlank(message = "Please provide the street")
    private String street;

    @NotBlank(message = "Please provide the city")
    @Pattern(regexp = "^[\\w ]+", message = "The city name should only contain the alphabets")
    private String city;

    @NotBlank(message = "PLease provide the state")
    @Pattern(regexp = "^[\\w ]+", message = "The state name should only contain the alphabets")
    private String state;

    private String landmark;

    @NotBlank(message = "Please provide the pin code")
    @Pattern(regexp = "^[\\d]{6}", message = "Please provide a correct Pin code")
    private String pinCode;
}
