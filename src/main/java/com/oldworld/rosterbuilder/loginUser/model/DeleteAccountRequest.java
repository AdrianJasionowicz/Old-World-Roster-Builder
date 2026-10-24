package com.oldworld.rosterbuilder.loginUser.model;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountRequest(
        @NotBlank
        String password
) {
}
