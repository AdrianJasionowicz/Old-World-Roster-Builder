package com.oldworld.rosterbuilder.unit;

import com.oldworld.rosterbuilder.upgrade.StatProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "unit_profile")
public class UnitProfile extends StatProfile {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
}
