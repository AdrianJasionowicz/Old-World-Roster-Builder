package com.oldworld.rosterbuilder.upgrade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "upgrade_stat_modifier")
public class UpgradeStatModifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "upgrade_id", nullable = false)
    private Upgrade upgrade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatType stat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModifierOperation operation;

    @Column(name = "modifier_value", nullable = false)
    private int value;

    UpgradeStatModifier(Upgrade upgrade, StatType stat, ModifierOperation operation, int value) {
        this.upgrade = upgrade;
        this.stat = stat;
        this.operation = operation;
        this.value = value;
    }
}
