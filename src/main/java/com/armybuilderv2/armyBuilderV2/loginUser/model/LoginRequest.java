package com.armybuilderv2.armyBuilderV2.loginUser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record LoginRequest (
     String username,
     String password
    ) {
}
