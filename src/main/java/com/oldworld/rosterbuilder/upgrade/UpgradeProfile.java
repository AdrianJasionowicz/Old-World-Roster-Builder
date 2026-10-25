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
public class UpgradeProfile extends StatProfile {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "upgrade_id", nullable = false)
    private Upgrade upgrade;
}
