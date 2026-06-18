package com.armybuilderv2.armyBuilderV2.upgrade;

import com.armybuilderv2.armyBuilderV2.upgrade.model.UpgradeView;
import org.springframework.stereotype.Service;

@Service
public class UpgradeMapper {


   public UpgradeView entityToView(Upgrade upgrade) {
       return new UpgradeView(upgrade.getId(), upgrade.getName(), upgrade.getPointsCost(), upgrade.getUpgradeType(), upgrade.getDescription());

   }
}
