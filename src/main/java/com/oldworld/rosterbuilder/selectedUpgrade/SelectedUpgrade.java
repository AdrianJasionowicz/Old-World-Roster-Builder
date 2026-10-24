package com.oldworld.rosterbuilder.selectedUpgrade;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SelectedUpgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "army_unit_id", nullable = false)
    private ArmyUnit armyUnit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_upgrade_id", nullable = false)
    private UnitUpgrade unitUpgrade;

    public SelectedUpgrade(ArmyUnit armyUnit, UnitUpgrade unitUpgrade) {
        this.armyUnit = armyUnit;
        this.unitUpgrade = unitUpgrade;
    }

    public Upgrade getUpgrade() {
        return unitUpgrade.getUpgrade();
    }

    public UpgradeType getUpgradeType() {
        return unitUpgrade.getUpgrade().getUpgradeType();
    }

    public double getPointsCost() {
        return unitUpgrade.getPointsCost();
    }

    public boolean isFor(Long unitUpgradeId) {
        return unitUpgrade.getId().equals(unitUpgradeId);
    }

    public void syncQuantityWith(int unitQuantity) {
        quantity = getUpgradeType().isBoughtPerModel() ? unitQuantity : 1;
    }

    public double getTotalCost() {
        return quantity * getPointsCost();
    }
}
