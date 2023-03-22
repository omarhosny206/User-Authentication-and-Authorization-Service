package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(
        @NotEmpty
        String firstName,
        @NotEmpty
        String lastName,
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        String password,
        @NotEmpty
        String role
) {
}
