package com.armybuilderv2.armyBuilderV2.upgrade;

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
    private boolean selected;
    private double quantity;

}
