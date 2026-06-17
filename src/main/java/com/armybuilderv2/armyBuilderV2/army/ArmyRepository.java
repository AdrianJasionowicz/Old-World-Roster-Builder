package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {
    List<Army> findByOwner(LoginUser owner);
}
