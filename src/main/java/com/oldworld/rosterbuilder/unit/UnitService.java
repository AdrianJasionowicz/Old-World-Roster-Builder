package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.unit.model.UnitRequest;
import com.oldworld.rosterbuilder.unit.model.UnitResponse;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final UpgradeService upgradeService;

    @Transactional
    UnitResponse addUnit(UnitRequest unitRequest) {
        var unit = unitMapper.mapUnitRqToUnit(unitRequest);
        for (var upgradeRequest : unitRequest.upgradesList()) {
            var upgrade = upgradeService.findOrCreate(upgradeRequest.name(), upgradeRequest.upgradeType(), upgradeRequest.description());
            var alreadyAdded = unit.getUnitUpgrades().stream()
                    .anyMatch(unitUpgrade -> unitUpgrade.getUpgrade() == upgrade);
            if (!alreadyAdded) {
                unit.addUnitUpgrade(new UnitUpgrade(unit, upgrade, upgradeRequest.pointsCost()));
            }
        }
        unitRepository.save(unit);
        log.info("Added unit '{}' ({}, {}) with {} upgrades", unit.getName(), unit.getUnitFaction(), unit.getUnitType(), unit.getUnitUpgrades().size());
        return unitMapper.mapUnitToUnitResponse(unit);
    }

    List<UnitResponse> getAllByFaction(UnitFaction faction) {
        return unitRepository.getAllByUnitFaction(faction).stream()
                .map(unitMapper::mapUnitToUnitResponse)
                .toList();
    }

    List<UnitFaction> getFactionsWithUnits() {
        return unitRepository.findFactionsWithUnits().stream().sorted().toList();
    }

    UnitResponse getUnitById(Long id) {
        var unit = unitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Unit with id " + id + " not found"));
        return unitMapper.mapUnitToUnitResponse(unit);
    }

    @Transactional
    void deleteById(Long id) {
        if (!unitRepository.existsById(id)) {
            throw new NotFoundException("Unit with id " + id + " not found");
        }
        unitRepository.deleteById(id);
        unitRepository.flush();
        log.info("Deleted unit {}", id);
    }
}
