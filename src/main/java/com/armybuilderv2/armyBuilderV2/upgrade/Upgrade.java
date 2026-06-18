package com.armybuilderv2.armyBuilderV2.upgrade;

import com.armybuilderv2.armyBuilderV2.unit.Unit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Upgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double pointsCost;
    @Enumerated(EnumType.STRING)
    private UpgradeType upgradeType;
    private String description;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonBackReference
    private Unit unit;
}
