package com.oldworld.rosterbuilder.loginUser.model;

import jakarta.validation.constraints.NotBlank;

public record UsernameChangeRequest(
        @NotBlank
        String newUsername,
        @NotBlank
        String password
) {
}
