package com.oldworld.rosterbuilder.specialRule;

import jakarta.validation.constraints.NotBlank;

public record SpecialRuleRequest(
        @NotBlank
        String name,
        String description
) {
}
