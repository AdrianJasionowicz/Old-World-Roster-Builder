package com.oldworld.rosterbuilder.upgrade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpgradeService {

    private final UpgradeRepository upgradeRepository;

    public Upgrade findOrCreate(String name, UpgradeType upgradeType, String description) {
        var trimmedName = name.trim();
        return upgradeRepository.findByNameAndUpgradeType(trimmedName, upgradeType)
                .orElseGet(() -> {
                    log.info("Adding new upgrade '{}' ({}) to the catalog", trimmedName, upgradeType);
                    return upgradeRepository.save(new Upgrade(trimmedName, upgradeType, description));
                });
    }
}
