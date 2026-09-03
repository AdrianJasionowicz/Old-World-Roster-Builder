package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyResponse;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.NoCreateArmyRequestException;
import com.armybuilderv2.armyBuilderV2.loginUser.CurrentUserService;
import com.armybuilderv2.armyBuilderV2.loginUser.LoginUser;
import com.armybuilderv2.armyBuilderV2.loginUser.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ArmyServiceTest {

    @Mock
    ArmyRepository armyRepository;
    @InjectMocks
    ArmyService armyService;
    @Mock
    ArmyMapper armyMapper;
    @Mock
    CurrentUserService currentUserService;

    @Test
    @DisplayName("Create army")
    void createArmyHappyPath() {
        CreateArmyRequest request = new CreateArmyRequest("test", "testDesc", 1.0, Faction.SKAVEN);
        CreateArmyResponse response = new CreateArmyResponse(null, "test", "testDesc", 1.0, Faction.SKAVEN);
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("test");
        loginUser.setPassword("test");
        loginUser.setEmail("test@test.com");
        loginUser.setArmies(new ArrayList<>());
        loginUser.setEnabled(true);
        loginUser.setId(10000L);
        loginUser.setRole(Role.ROLE_USER);

        Army army = new Army();
        army.setDescription(request.description());
        army.setName(request.name());
        army.setFaction(Faction.SKAVEN);
        army.setPointsLimit(request.pointsLimit());
        when(currentUserService.getCurrentUser()).thenReturn(loginUser);
        when(armyRepository.save(any(Army.class)))
                .thenReturn(army);


        CreateArmyResponse responseFromService = armyService.createArmy(request);
        verify(armyRepository).save(any(Army.class));
        assertEquals(response, responseFromService);
    }

    @Test
    @DisplayName("Create army with empty request")
    void createArmyNullRequest() {
        assertThrows(
                NoCreateArmyRequestException.class,
                () -> armyService.createArmy(null));
    }


    @Test
    @DisplayName("Delete army")
    void deleteArmyHappyPath() {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(10000L);

        Army army = new Army();
        army.setId(100L);
        army.setOwner(loginUser);

        when(armyRepository.findById(100L))
                .thenReturn(Optional.of(army));

        doNothing().when(currentUserService).validateArmyAccess(army);

        armyService.deleteArmy(100L);

        verify(armyRepository).findById(100L);
        verify(currentUserService).validateArmyAccess(army);
        verify(armyRepository).forceDelete(100L);
    }

    @Test
    @DisplayName("Delete army with empty id")
    void deleteArmyNullRequest() {
        assertThrows(ArmyNotFoundException.class, () -> armyService.deleteArmy(null));
    }




    @Test
    void getAllArmiesByUsername() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("test");
        loginUser.setEmail("test@test.com");

        Army army = new Army();
        army.setId(100L);
        army.setDescription("test");
        army.setOwner(loginUser);

        Army army2 = new Army();
        army2.setId(200L);
        army2.setDescription("test2");
        army2.setOwner(loginUser);

        List<Army> armyList = new ArrayList<>();
        armyList.add(army);
        armyList.add(army2);

        ArmyView armyView = new ArmyView();
        armyView.setId(100L);
        armyView.setDescription("test");

        ArmyView armyView2 = new ArmyView();
        armyView2.setId(200L);
        armyView2.setDescription("test2");

        List<ArmyView> armyViewList = new ArrayList<>();
        armyViewList.add(armyView);
        armyViewList.add(armyView2);

        when(currentUserService.getCurrentUser())
                .thenReturn(loginUser);

        when(armyRepository.findByOwner(loginUser))
                .thenReturn(armyList);

        when(armyMapper.makeView(army))
                .thenReturn(armyView);

        when(armyMapper.makeView(army2))
                .thenReturn(armyView2);

        List<ArmyView> armyViews = armyService.getAllArmiesByUsername();

        assertEquals(armyViewList, armyViews);

        verify(currentUserService).getCurrentUser();
        verify(armyRepository).findByOwner(loginUser);
        verify(armyMapper).makeView(army);
        verify(armyMapper).makeView(army2);
    }

    @Test
    @DisplayName("Get Army By Id")
    void getArmyByIdHappyPath() {
        Army army = new Army();
        army.setId(100L);
        army.setDescription("test");
        army.setName("test");

        ArmyView armyView = new ArmyView();
        armyView.setId(100L);
        armyView.setDescription("test");
        armyView.setName("test");

        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("test");
        loginUser.setPassword("test");
        loginUser.setEmail("test@test.com");
        loginUser.setArmies(new ArrayList<>());
        loginUser.setEnabled(true);
        loginUser.setId(10000L);
        loginUser.setRole(Role.ROLE_USER);

        army.setOwner(loginUser);

        doNothing().when(currentUserService).validateArmyAccess(army);

        when(armyRepository.findById(100L))
                .thenReturn(Optional.of(army));

        when(armyMapper.makeView(army))
                .thenReturn(armyView);

        ArmyView result = armyService.getArmyById(100L);

        verify(armyRepository).findById(100L);
        verify(currentUserService).validateArmyAccess(army);
        verify(armyMapper).makeView(army);

        assertEquals(armyView, result);
    }

    @Test
    @DisplayName("Get army with null in id")
    void createArmyNullId() {
        assertThrows(ArmyNotFoundException.class, () -> armyService.getArmyById(null));
    }


    @Test
    void getArmyById() {
    }

    @Test
    void addArmyUnit() {
    }

    @Test
    void editArmyName() {
    }

    @Test
    void updateArmyPointsLimit() {
    }

    @Test
    void getArmyOwnedByCurrentUser() {
    }
}