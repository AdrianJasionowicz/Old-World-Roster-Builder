package com.armybuilderv2.armyBuilderV2.armyUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmyUnitRepository extends JpaRepository<ArmyUnit, Long> {
    boolean existsArmyUnitById(Long id);

    @Modifying
    @Query("delete from ArmyUnit a where a.id = :id")
    void forceDelete(Long id);
}
