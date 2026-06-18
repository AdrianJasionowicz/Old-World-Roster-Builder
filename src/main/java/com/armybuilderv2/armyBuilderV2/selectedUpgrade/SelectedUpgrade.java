package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.upgrade.Upgrade;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelectedUpgrade  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "armyUnit_id")
    @JsonBackReference
    private ArmyUnit armyUnit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upgrade_id")
    private Upgrade upgrade;
}
