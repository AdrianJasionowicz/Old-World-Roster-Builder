package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.loginUser.LoginUser;
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
public class Army {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double pointsLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Faction faction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private LoginUser owner;

    @OneToMany(mappedBy = "army", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArmyUnit> armyUnitsList = new ArrayList<>();
}
