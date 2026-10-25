package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.specialRule.SpecialRule;
import com.oldworld.rosterbuilder.unitStats.UnitStats;
import com.oldworld.rosterbuilder.unitUpgrade.UnitUpgrade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double pointsCostPerUnit;

    private Integer minQuantity;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    private UnitFaction unitFaction;

    private Double magicItemsPointsLimit;

    private Double magicBannerPointsLimit;

    private boolean canHaveDetachments;

    private boolean canBeDetachment;

    private boolean fixedSize;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "unit_special_rule",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "special_rule_id")
    )
    private List<SpecialRule> specialRulesList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_stats_id")
    private UnitStats unitStats;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UnitUpgrade> unitUpgrades = new ArrayList<>();

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position")
    private List<UnitProfile> profiles = new ArrayList<>();

    public double magicItemsAllowance() {
        return magicItemsPointsLimit != null ? magicItemsPointsLimit : unitType.getMagicItemsPointsLimit();
    }

    public boolean canChangeSize() {
        return unitType.canChangeSize() && !fixedSize;
    }

    public void addUnitUpgrade(UnitUpgrade unitUpgrade) {
        unitUpgrade.setUnit(this);
        unitUpgrades.add(unitUpgrade);
    }
}
