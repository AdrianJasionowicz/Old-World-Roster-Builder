package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.loginUser.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArmyRepository extends JpaRepository<Army, Long> {

    List<Army> findByOwner(LoginUser owner);
}
