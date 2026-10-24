package com.oldworld.rosterbuilder.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> getAllByUnitFaction(UnitFaction unitFaction);

    @Query("select distinct u.unitFaction from Unit u")
    List<UnitFaction> findFactionsWithUnits();
}
