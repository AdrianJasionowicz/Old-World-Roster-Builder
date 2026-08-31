package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedUpgradeRepository  extends JpaRepository<SelectedUpgrade,Long> {

    @Modifying
    @Query("DELETE FROM SelectedUpgrade a WHERE a.id = :id")
    void forceDelete(@Param("id") Long id);

}
