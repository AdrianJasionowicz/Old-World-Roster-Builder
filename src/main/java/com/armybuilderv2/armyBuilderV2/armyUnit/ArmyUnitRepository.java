package com.armybuilderv2.armyBuilderV2.armyUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmyUnitRepository extends JpaRepository<ArmyUnit, Long> {
    boolean existsArmyUnitById(Long id);
}
