package com.armybuilderv2.armyBuilderV2.upgrade;

import org.springframework.stereotype.Service;

@Service
public class UpgradeService {
    private UpgradeRepository upgradeRepository;

    public UpgradeService(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }
}
