package com.armybuilderv2.armyBuilderV2.loginUser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}
