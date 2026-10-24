package com.oldworld.rosterbuilder.upgrade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "upgrade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "upgrade_type"})
)
public class Upgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "upgrade_type", nullable = false)
    private UpgradeType upgradeType;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "upgrade_group_id")
    private UpgradeGroup upgradeGroup;

    @Column(name = "unique_in_army", nullable = false)
    private boolean uniqueInArmy;

    @Enumerated(EnumType.STRING)
    private RuneKind rune;

    @Column(nullable = false)
    private boolean champion;

    @OneToMany(mappedBy = "upgrade", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 50)
    private List<UpgradeStatModifier> statModifiers = new ArrayList<>();

    @OneToMany(mappedBy = "upgrade", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position")
    @BatchSize(size = 50)
    private List<UpgradeProfile> profiles = new ArrayList<>();

    public Upgrade(String name, UpgradeType upgradeType, String description) {
        this.name = name;
        this.upgradeType = upgradeType;
        this.description = description;
    }

    public void addStatModifier(StatType stat, ModifierOperation operation, int value) {
        statModifiers.add(new UpgradeStatModifier(this, stat, operation, value, false));
    }

    public void addChampionStatModifier(StatType stat, ModifierOperation operation, int value) {
        statModifiers.add(new UpgradeStatModifier(this, stat, operation, value, true));
    }
}
