package com.armybuilderv2.armyBuilderV2.loginUser.model;

public record UsernameChangeRequest(
        String newUsername,
        String password
) {
}
