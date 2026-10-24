package com.oldworld.rosterbuilder.unitUpgrade;

import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.upgrade.Upgrade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "unit_upgrade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"unit_id", "upgrade_id"})
)
public class UnitUpgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "upgrade_id", nullable = false)
    private Upgrade upgrade;

    @Column(nullable = false)
    private double pointsCost;

    public UnitUpgrade(Unit unit, Upgrade upgrade, double pointsCost) {
        this.unit = unit;
        this.upgrade = upgrade;
        this.pointsCost = pointsCost;
    }
}
