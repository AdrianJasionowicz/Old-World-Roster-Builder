package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.ArmyView;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyRequest;
import com.armybuilderv2.armyBuilderV2.army.model.CreateArmyResponse;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.NoCreateArmyRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArmyServiceTest {

    @Mock
    ArmyRepository armyRepository;
    @InjectMocks
    ArmyService armyService;
    @InjectMocks
    ArmyMapper armyMapper;

    @Test
    @DisplayName("Create army")
    void createArmyHappyPath() {
        CreateArmyRequest request = new CreateArmyRequest("test", "testDesc", 1.0, Faction.SKAVEN);
        CreateArmyResponse response = new CreateArmyResponse(null, "test", "testDesc", 1.0, Faction.SKAVEN);
        Army army = new Army();
        army.setDescription(request.description());
        army.setName(request.name());
        army.setFaction(Faction.SKAVEN);
        army.setPointsLimit(request.pointsLimit());
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
        Army army = new Army();
        army.setId(100L);
        when(armyRepository.findById(100L)).thenReturn(Optional.of(army));


        armyService.deleteArmy(100L);

        verify(armyRepository).deleteById(100L);

    }

    @Test
    @DisplayName("Delete army with empty id")
    void deleteArmyNullRequest() {
        assertThrows(ArmyNotFoundException.class, () -> armyService.deleteArmy(null));
    }

    @Test
    @DisplayName("Check is Army view working correctly")
    void checkIsArmyViewWorkingCorrectly() {
        Army army = new Army();
        army.setId(100L);
        army.setDescription("test");
        army.setName("test");
        army.setFaction(Faction.SKAVEN);
        army.setPointsLimit(200.0);

        ArmyView armyView = new ArmyView();
        armyView.setId(100L);
        armyView.setDescription("test");
        armyView.setName("test");
        armyView.setFaction(Faction.SKAVEN);
        armyView.setPointsLimit(200.0);

        ArmyView result = armyMapper.makeView(army);
        assertEquals(armyView, result);
    }

//    @Test
//    void getAllArmiesByUsername() {
//        LoginUser loginUser = new LoginUser();
//        loginUser.setUsername("test");
//        loginUser.setEmail("test@test.com");
//
//        Army army = new Army();
//        army.setId(100L);
//        army.setDescription("test");
//        army.setOwner(loginUser);
//        Army army2 = new Army();
//        army2.setId(200L);
//        army2.setDescription("test2");
//        army2.setOwner(loginUser);
//        List<Army> armyList = new ArrayList<>();
//        armyList.add(army);
//        armyList.add(army2);
//        ArmyView armyView = new ArmyView();
//        armyView.setId(100L);
//        armyView.setDescription("test");
//        ArmyView armyView2 = new ArmyView();
//        armyView2.setId(200L);
//        armyView2.setDescription("test2");
//        List<ArmyView> armyViewList = new ArrayList<>();
//        armyViewList.add(armyView);
//        armyViewList.add(armyView2);
//        when(armyRepository.findByOwner(loginUser)).thenReturn(armyList);
//        List<ArmyView> armyViews = armyService.getAllArmiesByUsername();
//        assertEquals(armyViewList,result);
//
//    }

    @Test
    @DisplayName("Get Army By Id ")
    void getArmyByIdHappyPath() {
        Army army = new Army();
        army.setId(100L);
        army.setDescription("test");
        army.setName("test");

        ArmyView armyView = new ArmyView();
        armyView.setId(100L);
        armyView.setDescription("test");
        armyView.setName("test");

        when(armyRepository.findById(100L)).thenReturn(Optional.of(army));
        when(armyMapper.makeView(army)).thenReturn(armyView);
        ArmyView result = armyService.getArmyById(100L);

        verify(armyRepository).findById(100L);
        verify(armyMapper).makeView(army);
        assertEquals(armyView, result);    }

    @Test
    @DisplayName("Get army with null in id")
    void createArmyNullId() {
        assertThrows(ArmyNotFoundException.class, () -> armyService.getArmyById(null));
    }


}