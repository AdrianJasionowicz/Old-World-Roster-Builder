package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyRepository;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnitRepository;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.exception.UnauthorizedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentUserServiceTest {

    @Mock
    LoginUserRepository loginUserRepository;
    @Mock
    ArmyRepository armyRepository;
    @Mock
    ArmyUnitRepository armyUnitRepository;
    @InjectMocks
    CurrentUserService currentUserService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private LoginUser logIn(Long id, String username) {
        var loginUser = new LoginUser();
        loginUser.setId(id);
        loginUser.setUsername(username);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, List.of()));
        when(loginUserRepository.findByUsername(username)).thenReturn(Optional.of(loginUser));
        return loginUser;
    }

    private Army armyOwnedBy(Long ownerId) {
        var owner = new LoginUser();
        owner.setId(ownerId);
        var army = new Army();
        army.setId(10L);
        army.setOwner(owner);
        return army;
    }

    @Test
    void getCurrentUserReturnsLoggedInUser() {
        var loginUser = logIn(1L, "admin");

        assertSame(loginUser, currentUserService.getCurrentUser());
    }

    @Test
    void getCurrentUserWithoutAuthenticationThrows() {
        assertThrows(UnauthorizedException.class, () -> currentUserService.getCurrentUser());
    }

    @Test
    void ownerGetsTheirArmy() {
        logIn(1L, "owner");
        var army = armyOwnedBy(1L);
        when(armyRepository.findById(10L)).thenReturn(Optional.of(army));

        assertSame(army, currentUserService.getArmyOwnedByCurrentUser(10L));
    }

    @Test
    void otherUserIsForbidden() {
        logIn(2L, "intruder");
        when(armyRepository.findById(10L)).thenReturn(Optional.of(armyOwnedBy(1L)));

        assertThrows(ForbiddenException.class, () -> currentUserService.getArmyOwnedByCurrentUser(10L));
    }

    @Test
    void missingArmyIsNotFound() {
        when(armyRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> currentUserService.getArmyOwnedByCurrentUser(10L));
    }

    @Test
    void getCurrentUserOfDeletedUserThrows() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("deleted", null, List.of()));
        when(loginUserRepository.findByUsername("deleted")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> currentUserService.getCurrentUser());
    }

    @Test
    void ownerGetsTheirArmyUnit() {
        logIn(1L, "owner");
        var armyUnit = new ArmyUnit();
        armyUnit.setArmy(armyOwnedBy(1L));
        when(armyUnitRepository.findById(20L)).thenReturn(Optional.of(armyUnit));

        assertSame(armyUnit, currentUserService.getArmyUnitOwnedByCurrentUser(20L));
    }

    @Test
    void otherUserIsForbiddenFromArmyUnit() {
        logIn(2L, "intruder");
        var armyUnit = new ArmyUnit();
        armyUnit.setArmy(armyOwnedBy(1L));
        when(armyUnitRepository.findById(20L)).thenReturn(Optional.of(armyUnit));

        assertThrows(ForbiddenException.class, () -> currentUserService.getArmyUnitOwnedByCurrentUser(20L));
    }

    @Test
    void missingArmyUnitIsNotFound() {
        when(armyUnitRepository.findById(20L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> currentUserService.getArmyUnitOwnedByCurrentUser(20L));
    }

    @Test
    void armyUnitWithoutLoggedInUserIsUnauthorized() {
        var armyUnit = new ArmyUnit();
        armyUnit.setArmy(armyOwnedBy(1L));
        when(armyUnitRepository.findById(20L)).thenReturn(Optional.of(armyUnit));

        assertThrows(UnauthorizedException.class, () -> currentUserService.getArmyUnitOwnedByCurrentUser(20L));
    }
}
