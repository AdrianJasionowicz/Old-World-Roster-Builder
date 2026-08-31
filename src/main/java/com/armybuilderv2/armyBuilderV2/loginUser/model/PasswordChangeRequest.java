package com.armybuilderv2.armyBuilderV2.loginUser.model;

public record PasswordChangeRequest(
        String password,
        String newPassword
) {
}
