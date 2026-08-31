package com.armybuilderv2.armyBuilderV2.loginUser.model;

public record EmailChangeRequest(
        String password,
        String newEmail
) {
}
