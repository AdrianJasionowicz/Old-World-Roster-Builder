package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.specialRule.SpecialRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double minQuantity;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    @Enumerated(EnumType.STRING)
    private UnitFaction unitFaction;

    private List<SpecialRule> specialRulesList;
    // private UnitStats unitStats;
    // private Upgrade upgrade;



}
