package com.armybuilderv2.armyBuilderV2.army.model;

import com.armybuilderv2.armyBuilderV2.army.Faction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArmyView {
    private Long id;
    private String name;
    private String description;
    private Double pointsLimit;
    private Faction faction;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArmyView armyView = (ArmyView) o;
        return Objects.equals(id, armyView.id) && Objects.equals(name, armyView.name) && Objects.equals(description, armyView.description) && Objects.equals(pointsLimit, armyView.pointsLimit) && faction == armyView.faction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, pointsLimit, faction);
    }
}
