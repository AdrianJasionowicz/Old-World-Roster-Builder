package com.oldworld.rosterbuilder.loginUser.model;

import com.oldworld.rosterbuilder.loginUser.Role;

public record LoginStatus(
        String username,
        Role role,
        String email
) {
}
