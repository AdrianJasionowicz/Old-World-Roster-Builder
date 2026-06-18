package com.armybuilderv2.armyBuilderV2.armyUnit;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ArmyUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private double totalCost;
    @ManyToOne
    @JoinColumn(name = "army_id")
    private Army army;
    @ManyToOne
    private Unit unit;
    @OneToMany(mappedBy = "armyUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SelectedUpgrade> selectedUpgradesList = new ArrayList<>();

}
