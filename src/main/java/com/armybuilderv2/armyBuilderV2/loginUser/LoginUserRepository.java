package com.armybuilderv2.armyBuilderV2.loginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByUsername(String username);
    Optional<LoginUser> findByUsernameOptional(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
