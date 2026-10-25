package com.oldworld.rosterbuilder.army;

import com.oldworld.rosterbuilder.army.model.ArmyView;
import com.oldworld.rosterbuilder.army.model.CreateArmyRequest;
import com.oldworld.rosterbuilder.army.model.CreateArmyResponse;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.exception.UnauthorizedException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.loginUser.LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArmyServiceTest {

    @Mock
    ArmyRepository armyRepository;
    @Mock
    ArmyMapper armyMapper;
    @Mock
    CurrentUserService currentUserService;
    @InjectMocks
    ArmyService armyService;

    private static LoginUser user() {
        var loginUser = new LoginUser();
        loginUser.setId(1L);
        loginUser.setUsername("test");
        return loginUser;
    }

    private static Army army(Long id) {
        var army = new Army();
        army.setId(id);
        army.setName("Army " + id);
        army.setPointsLimit(2000.0);
        army.setFaction(Faction.SKAVEN);
        army.setOwner(user());
        return army;
    }

    private static ArmyView view(Army army) {
        return new ArmyView(army.getId(), army.getName(), army.getDescription(), army.getPointsLimit(), army.getFaction(), List.of(), List.of());
    }

    @Test
    @DisplayName("createArmy saves an army owned by the current user")
    void createArmy() {
        var request = new CreateArmyRequest("test", "testDesc", 1.0, Faction.SKAVEN);
        when(currentUserService.getCurrentUser()).thenReturn(user());

        CreateArmyResponse response = armyService.createArmy(request);

        verify(armyRepository).save(any(Army.class));
        assertEquals(new CreateArmyResponse(null, "test", "testDesc", 1.0, Faction.SKAVEN), response);
    }

    @Test
    @DisplayName("deleteArmy deletes the army through the repository (units go by cascade)")
    void deleteArmy() {
        var army = army(100L);
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenReturn(army);

        armyService.deleteArmy(100L);

        verify(armyRepository).delete(army);
    }

    @Test
    @DisplayName("editArmyName renames the army")
    void editArmyName() {
        var army = army(100L);
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenReturn(army);

        armyService.editArmyName(100L, "BCAA");

        assertEquals("BCAA", army.getName());
    }

    @Test
    @DisplayName("updateArmyPointsLimit changes the limit")
    void updateArmyPointsLimit() {
        var army = army(100L);
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenReturn(army);

        armyService.updateArmyPointsLimit(100L, 100.0);

        assertEquals(100.0, army.getPointsLimit());
    }

    @Test
    @DisplayName("getAllArmiesOfCurrentUser maps every army of the user")
    void getAllArmiesOfCurrentUser() {
        var loginUser = user();
        var army = army(100L);
        var army2 = army(200L);
        when(currentUserService.getCurrentUser()).thenReturn(loginUser);
        when(armyRepository.findByOwner(loginUser)).thenReturn(List.of(army, army2));
        when(armyMapper.makeView(army)).thenReturn(view(army));
        when(armyMapper.makeView(army2)).thenReturn(view(army2));

        assertEquals(List.of(view(army), view(army2)), armyService.getAllArmiesOfCurrentUser());
    }

    @Test
    @DisplayName("getArmyById returns the view of an owned army")
    void getArmyById() {
        var army = army(100L);
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenReturn(army);
        when(armyMapper.makeView(army)).thenReturn(view(army));

        assertEquals(view(army), armyService.getArmyById(100L));
    }

    @Test
    @DisplayName("createArmy without a logged in user saves nothing")
    void createArmyNotLoggedIn() {
        when(currentUserService.getCurrentUser()).thenThrow(new UnauthorizedException("User not logged in"));

        assertThrows(UnauthorizedException.class, () -> armyService.createArmy(new CreateArmyRequest("test", null, 1.0, Faction.SKAVEN)));
        verifyNoInteractions(armyRepository);
    }

    @Test
    @DisplayName("createArmy passes a database error on")
    void createArmyDatabaseError() {
        when(currentUserService.getCurrentUser()).thenReturn(user());
        when(armyRepository.save(any(Army.class))).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class, () -> armyService.createArmy(new CreateArmyRequest("test", null, 1.0, Faction.SKAVEN)));
    }

    @Test
    @DisplayName("deleteArmy of another user's army is forbidden and deletes nothing")
    void deleteArmyForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyService.deleteArmy(100L));
        verify(armyRepository, never()).delete(any());
    }

    @Test
    @DisplayName("deleteArmy of a missing army is not found and deletes nothing")
    void deleteArmyNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new NotFoundException("Army with id 100 not found"));

        assertThrows(NotFoundException.class, () -> armyService.deleteArmy(100L));
        verify(armyRepository, never()).delete(any());
    }

    @Test
    @DisplayName("editArmyName of another user's army is forbidden")
    void editArmyNameForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyService.editArmyName(100L, "BCAA"));
    }

    @Test
    @DisplayName("editArmyName of a missing army is not found")
    void editArmyNameNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new NotFoundException("Army with id 100 not found"));

        assertThrows(NotFoundException.class, () -> armyService.editArmyName(100L, "BCAA"));
    }

    @Test
    @DisplayName("updateArmyPointsLimit of another user's army is forbidden")
    void updateArmyPointsLimitForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyService.updateArmyPointsLimit(100L, 100.0));
    }

    @Test
    @DisplayName("updateArmyPointsLimit of a missing army is not found")
    void updateArmyPointsLimitNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new NotFoundException("Army with id 100 not found"));

        assertThrows(NotFoundException.class, () -> armyService.updateArmyPointsLimit(100L, 100.0));
    }

    @Test
    @DisplayName("getAllArmiesOfCurrentUser of a user without armies is empty")
    void getAllArmiesOfCurrentUserEmpty() {
        var loginUser = user();
        when(currentUserService.getCurrentUser()).thenReturn(loginUser);
        when(armyRepository.findByOwner(loginUser)).thenReturn(List.of());

        assertTrue(armyService.getAllArmiesOfCurrentUser().isEmpty());
        verifyNoInteractions(armyMapper);
    }

    @Test
    @DisplayName("getAllArmiesOfCurrentUser without a logged in user throws UnauthorizedException")
    void getAllArmiesOfCurrentUserNotLoggedIn() {
        when(currentUserService.getCurrentUser()).thenThrow(new UnauthorizedException("User not logged in"));

        assertThrows(UnauthorizedException.class, () -> armyService.getAllArmiesOfCurrentUser());
    }

    @Test
    @DisplayName("getArmyById of a missing army is not found")
    void getArmyByIdNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new NotFoundException("Army with id 100 not found"));

        assertThrows(NotFoundException.class, () -> armyService.getArmyById(100L));
        verifyNoInteractions(armyMapper);
    }

    @Test
    @DisplayName("getArmyById of another user's army is forbidden")
    void getArmyByIdForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(100L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> armyService.getArmyById(100L));
    }
}
