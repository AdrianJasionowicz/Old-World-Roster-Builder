package com.oldworld.rosterbuilder.armyUnit;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.upgrade.StatProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ArmyUnit {

    public static final int DETACHMENT_MIN_SIZE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    private double totalCost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "army_id", nullable = false)
    private Army army;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @OneToMany(mappedBy = "armyUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectedUpgrade> selectedUpgradesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ArmyUnit parent;

    @OneToMany(mappedBy = "parent")
    private List<ArmyUnit> detachments = new ArrayList<>();

    public boolean isDetachment() {
        return parent != null;
    }

    public int getMinQuantity() {
        return isDetachment() ? DETACHMENT_MIN_SIZE : unit.getMinQuantity();
    }

    public List<StatProfile> getProfiles() {
        var upgradeProfiles = selectedUpgradesList.stream()
                .flatMap(selectedUpgrade -> selectedUpgrade.getUpgrade().getProfiles().stream());
        return Stream.concat(unit.getProfiles().stream(), upgradeProfiles)
                .map(StatProfile.class::cast)
                .toList();
    }
}
