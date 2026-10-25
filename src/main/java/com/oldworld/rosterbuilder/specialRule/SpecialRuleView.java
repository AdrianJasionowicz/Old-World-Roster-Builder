package com.oldworld.rosterbuilder.specialRule;

public record SpecialRuleView(
        String name,
        String description
) {

    public static SpecialRuleView from(SpecialRule specialRule) {
        return new SpecialRuleView(specialRule.getName(), specialRule.getDescription());
    }
}
