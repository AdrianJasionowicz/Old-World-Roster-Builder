package com.oldworld.rosterbuilder.specialRule;

import com.oldworld.rosterbuilder.army.Faction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialRuleService {

    private final SpecialRuleRepository specialRuleRepository;

    public SpecialRule findOrCreate(String name, String description) {
        var trimmedName = name.trim();
        return specialRuleRepository.findByName(trimmedName)
                .orElseGet(() -> {
                    log.info("Adding new special rule '{}' to the catalog", trimmedName);
                    return specialRuleRepository.save(new SpecialRule(null, trimmedName, description));
                });
    }

    public List<SpecialRuleView> getFactionRules(Faction faction) {
        return specialRuleRepository.findFactionRules(faction.name()).stream()
                .map(SpecialRuleView::from)
                .toList();
    }
}
