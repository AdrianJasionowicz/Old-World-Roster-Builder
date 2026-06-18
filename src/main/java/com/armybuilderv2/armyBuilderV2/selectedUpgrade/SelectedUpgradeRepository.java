package com.armybuilderv2.armyBuilderV2.selectedUpgrade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedUpgradeRepository  extends JpaRepository<SelectedUpgrade,Long> {

}
