package com.oldworld.rosterbuilder.loginUser.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailChangeRequest(
        @NotBlank
        String password,
        @NotBlank
        @Email
        String newEmail
) {
}
