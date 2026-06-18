package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRule;
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
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double pointsCostPerUnit;
    private Integer minQuantity;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    @Enumerated(EnumType.STRING)
    private UnitFaction unitFaction;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "unit_special_rule",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "special_rule_id")
    )
    private List<SpecialRule> specialRulesList;
    // private UnitStats unitStats;
    @OneToMany(mappedBy = "unit", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Upgrade> upgradesList = new ArrayList<>();



}
