package com.armybuilderv2.armyBuilderV2.factionRule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FactionRule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
}
