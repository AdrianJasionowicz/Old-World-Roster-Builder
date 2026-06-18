package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "army", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<ArmyUnit> armyUnitsList = new ArrayList<>();

}
