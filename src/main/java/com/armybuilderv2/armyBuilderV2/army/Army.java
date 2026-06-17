package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Army {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double pointsLimit;
    @Enumerated(EnumType.STRING)
    private Faction faction;
    @ManyToOne
    private LoginUser owner;
    // private List<ArmyUnit> armyUnitList;

}
