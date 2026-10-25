package com.oldworld.rosterbuilder.specialRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface SpecialRuleRepository extends JpaRepository<SpecialRule, Long> {

    Optional<SpecialRule> findByName(String name);

    @Query(value = """
            SELECT s.* FROM special_rule s
            JOIN faction_special_rule f ON f.special_rule_id = s.id
            WHERE f.faction = :faction
            ORDER BY f.position
            """, nativeQuery = true)
    List<SpecialRule> findFactionRules(String faction);
}
