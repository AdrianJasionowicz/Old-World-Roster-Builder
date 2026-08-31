package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {
    List<Army> findByOwner(LoginUser owner);

    /*
 TODO:
 JpaRepository.delete()/deleteById() do not work correctly here.
 Temporary solution uses JPQL DELETE.
 Investigate after upgrading Spring Boot / Hibernate.
     */

    @Modifying
    @Query("delete from Army a where a.id = :id")
    void forceDelete(@Param("id") Long id);
}
