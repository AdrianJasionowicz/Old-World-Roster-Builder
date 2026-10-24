package com.oldworld.rosterbuilder.upgrade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "upgrade_profile")
public class UpgradeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "upgrade_id", nullable = false)
    private Upgrade upgrade;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private String name;

    private String m;
    private String ws;
    private String bs;
    private String s;
    private String t;
    private String w;
    private String i;
    private String a;
    private String ld;

    @Column(name = "basic_save")
    private String basicSave;

    @Column(name = "ward_save")
    private String wardSave;
}
