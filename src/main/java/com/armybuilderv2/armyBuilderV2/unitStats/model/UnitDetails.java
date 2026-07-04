package com.armybuilderv2.armyBuilderV2.unitStats.model;

import java.util.List;

public record UnitDetails(
         Integer m,
         Integer ws,
         Integer bs,
         Integer s,
         Integer t,
         Integer w,
         Integer i,
         Integer a,
         Integer ld,
         Integer basicSave,
         Integer wardSave,
         List<UpgradeName> upgradeNamesList
) {
}
