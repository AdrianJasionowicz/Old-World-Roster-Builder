package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import org.springframework.stereotype.Service;

@Service
public class SelectedUpgradeMapper {

public SelectedUpgrade mapUpgradeToSelectedUpgrade(Upgrade upgrade) {
    SelectedUpgrade selectedUpgrade = new SelectedUpgrade();
    selectedUpgrade.setUpgrade(upgrade);
    return selectedUpgrade;
}

}
