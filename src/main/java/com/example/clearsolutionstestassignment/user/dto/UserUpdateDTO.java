package com.example.clearsolutionstestassignment.user.dto;

import com.example.clearsolutionstestassignment.validation.age.AgeLimit;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserUpdateDTO {
    @Email(message = "Email is not valid", regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    private String firstName;

    private String lastName;

    @AgeLimit(message = "User should not be under the age of 18")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
