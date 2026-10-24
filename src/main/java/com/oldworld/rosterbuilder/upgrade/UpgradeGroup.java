package com.oldworld.rosterbuilder.upgrade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "upgrade_group")
public class UpgradeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer maxChoices;

    private Double lordPointsLimit;

    private Double heroPointsLimit;

    public UpgradeGroup(String name, Integer maxChoices, Double lordPointsLimit, Double heroPointsLimit) {
        this.name = name;
        this.maxChoices = maxChoices;
        this.lordPointsLimit = lordPointsLimit;
        this.heroPointsLimit = heroPointsLimit;
    }
}
