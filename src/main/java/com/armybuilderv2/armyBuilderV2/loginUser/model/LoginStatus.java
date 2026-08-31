package com.armybuilderv2.armyBuilderV2.loginUser.model;

import com.armybuilderv2.armyBuilderV2.loginUser.Role;

public record LoginStatus(
        String username,
        Role role,
        String email

) {
}
