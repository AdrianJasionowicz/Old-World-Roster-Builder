package com.oldworld.rosterbuilder.loginUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface LoginUserRepository extends JpaRepository<LoginUser, Long> {

    Optional<LoginUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
