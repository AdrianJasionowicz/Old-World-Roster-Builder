package com.oldworld.rosterbuilder.loginUser.model;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(
        @NotBlank
        String password,
        @NotBlank
        String newPassword
) {
}
