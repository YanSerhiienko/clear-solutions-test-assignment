package com.example.clearsolutionstestassignment.user.dto;

import com.example.clearsolutionstestassignment.validation.age.AgeLimit;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDTO {
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be in format abc@mail.com", regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "First name should not be null")
    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotNull(message = "Last name should not be null")
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @NotNull(message = "Birth date should not be null")
    @AgeLimit(message = "User should not be under the age of 18")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
