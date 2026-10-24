package com.oldworld.rosterbuilder.upgrade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UpgradeRepository extends JpaRepository<Upgrade, Long> {

    Optional<Upgrade> findByNameAndUpgradeType(String name, UpgradeType upgradeType);
}
