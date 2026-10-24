package com.oldworld.rosterbuilder.unitStats;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unitStats.model.ChampionDetails;
import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import com.oldworld.rosterbuilder.unitStats.model.UpgradeName;
import com.oldworld.rosterbuilder.upgrade.StatType;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeStatModifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oldworld.rosterbuilder.upgrade.StatType.*;

@Service
@RequiredArgsConstructor
public class StatsCalculatorService {

    private final CurrentUserService currentUserService;
    private final StatsCalculatorValidatorService statsCalculatorValidatorService;

    UnitDetails getUnitDetails(Long armyUnitId) {
        return calculate(currentUserService.getArmyUnitOwnedByCurrentUser(armyUnitId));
    }

    public UnitDetails calculate(ArmyUnit armyUnit) {
        return details(armyUnit, false);
    }

    public Optional<ChampionDetails> calculateChampion(ArmyUnit armyUnit) {
        return armyUnit.getSelectedUpgradesList().stream()
                .map(SelectedUpgrade::getUpgrade)
                .filter(Upgrade::isChampion)
                .findFirst()
                .map(champion -> new ChampionDetails(champion.getName(), details(armyUnit, true)));
    }

    private UnitDetails details(ArmyUnit armyUnit, boolean champion) {
        var selectedUpgrades = armyUnit.getSelectedUpgradesList();
        var stats = calculateStats(armyUnit.getUnit().getUnitStats(), selectedUpgrades, champion);
        var upgradeNames = selectedUpgrades.stream()
                .map(selectedUpgrade -> new UpgradeName(selectedUpgrade.getUpgrade().getName()))
                .toList();
        return new UnitDetails(
                stats.get(M), stats.get(WS), stats.get(BS), stats.get(S), stats.get(T),
                stats.get(W), stats.get(I), stats.get(A), stats.get(LD),
                statsCalculatorValidatorService.toDisplaySave(stats.get(ARMOUR_SAVE)),
                statsCalculatorValidatorService.toDisplaySave(stats.get(WARD_SAVE)),
                upgradeNames);
    }

    Map<StatType, Integer> calculateStats(UnitStats unitStats, List<SelectedUpgrade> selectedUpgrades, boolean champion) {
        var stats = baseStats(unitStats);
        selectedUpgrades.stream()
                .flatMap(selectedUpgrade -> selectedUpgrade.getUpgrade().getStatModifiers().stream())
                .filter(modifier -> champion || !modifier.isChampionOnly())
                .sorted(Comparator.comparingInt(modifier -> modifier.getOperation().getPhase()))
                .forEach(modifier -> apply(stats, modifier));
        return stats;
    }

    private void apply(Map<StatType, Integer> stats, UpgradeStatModifier modifier) {
        stats.merge(modifier.getStat(), modifier.getValue(), modifier.getOperation()::apply);
    }

    private Map<StatType, Integer> baseStats(UnitStats unitStats) {
        var stats = new EnumMap<StatType, Integer>(StatType.class);
        stats.put(M, valueOrZero(unitStats.getM()));
        stats.put(WS, valueOrZero(unitStats.getWs()));
        stats.put(BS, valueOrZero(unitStats.getBs()));
        stats.put(S, valueOrZero(unitStats.getS()));
        stats.put(T, valueOrZero(unitStats.getT()));
        stats.put(W, valueOrZero(unitStats.getW()));
        stats.put(I, valueOrZero(unitStats.getI()));
        stats.put(A, valueOrZero(unitStats.getA()));
        stats.put(LD, valueOrZero(unitStats.getLd()));
        stats.put(ARMOUR_SAVE, statsCalculatorValidatorService.toCalculationSave(unitStats.getBasicSave()));
        stats.put(WARD_SAVE, statsCalculatorValidatorService.toCalculationSave(unitStats.getWardSave()));
        return stats;
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }
}
