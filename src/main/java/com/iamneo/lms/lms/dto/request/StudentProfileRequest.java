package com.iamneo.lms.lms.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileRequest {

    @Pattern(regexp = "[\\w ]+", message = "Please provide a correct name")
    private String name;

    @Past(message = "Please provide a correct date of birth")
    private Date dateOfBirth;

    @PastOrPresent(message = "Please provide a correct date of joining")
    private Date dateOfJoining;

    @NotNull(message = "Please provide a contact info")
    // @NotEmpty(message = "Please provide correct contact info")
    private ContactRequest contact;

    @NotNull(message = "Please provide an acedemic info")
    // @NotEmpty(message = "Please provide a correct academic info")
    private AcademicInfoRequest academicInfo;
}
