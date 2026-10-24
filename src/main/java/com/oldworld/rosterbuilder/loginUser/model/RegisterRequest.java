package com.oldworld.rosterbuilder.loginUser.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email
) {
}
