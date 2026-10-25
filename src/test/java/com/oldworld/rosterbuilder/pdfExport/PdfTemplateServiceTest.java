package com.oldworld.rosterbuilder.pdfExport;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyPointsService;
import com.oldworld.rosterbuilder.army.Faction;
import com.oldworld.rosterbuilder.army.model.ArmyPointsView;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.unit.Unit;
import com.oldworld.rosterbuilder.unit.UnitProfile;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitStats.StatsCalculatorService;
import com.oldworld.rosterbuilder.unitStats.model.ChampionDetails;
import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import com.oldworld.rosterbuilder.upgrade.UpgradeProfile;
import com.oldworld.rosterbuilder.upgrade.UpgradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.oldworld.rosterbuilder.support.UpgradeFixtures.selected;
import static com.oldworld.rosterbuilder.support.UpgradeFixtures.unitUpgrade;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfTemplateServiceTest {

    @Mock
    CurrentUserService currentUserService;
    @Mock
    StatsCalculatorService statsCalculatorService;
    @Mock
    ArmyPointsService armyPointsService;
    @InjectMocks
    PdfTemplateService pdfTemplateService;

    private static final UnitDetails STATS = new UnitDetails(5, 3, 3, 3, 3, 1, 4, 1, 5, 6, null, List.of());

    private static Army army() {
        var army = new Army();
        army.setId(1L);
        army.setName("Clan Mors");
        army.setPointsLimit(2000.0);
        army.setFaction(Faction.SKAVEN);
        return army;
    }

    private static ArmyUnit add(Army army, String name, UnitType unitType, int quantity) {
        var unit = new Unit();
        unit.setName(name);
        unit.setUnitType(unitType);
        var armyUnit = new ArmyUnit();
        armyUnit.setUnit(unit);
        armyUnit.setArmy(army);
        armyUnit.setQuantity(quantity);
        armyUnit.setTotalCost(quantity * 5.0);
        army.getArmyUnitsList().add(armyUnit);
        return armyUnit;
    }

    private void owned(Army army) {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenReturn(army);
        when(armyPointsService.calculateArmyPoints(army))
                .thenReturn(new ArmyPointsView(100, 2000, null, null, null, null, null, List.of(), false));
        var used = new EnumMap<UnitType, Double>(UnitType.class);
        for (var unitType : UnitType.values()) {
            used.put(unitType, 0.0);
        }
        when(armyPointsService.usedPointsByType(army)).thenReturn(used);
    }

    private static String text(byte[] pdf) throws IOException {
        try (var document = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdf)))) {
            var text = new StringBuilder();
            for (var page = 1; page <= document.getNumberOfPages(); page++) {
                text.append(PdfTextExtractor.getTextFromPage(document.getPage(page))).append('\n');
            }
            return text.toString();
        }
    }

    @Test
    @DisplayName("generateArmyPdf lists the army, its units, champion, profiles and upgrades")
    void generateArmyPdf() throws IOException {
        var army = army();
        var clanrats = add(army, "Clanrat", UnitType.CORE, 20);
        var ratlingGun = selected(unitUpgrade(2L, "Ratling Gun", UpgradeType.WEAPON_TEAM, 55));
        ratlingGun.setQuantity(1);
        var profile = new UpgradeProfile();
        profile.setName("Ratling Gun Team");
        profile.setW("2");
        profile.setBasicSave("6");
        ratlingGun.getUpgrade().getProfiles().add(profile);
        clanrats.getSelectedUpgradesList().add(ratlingGun);
        owned(army);
        when(statsCalculatorService.calculate(clanrats)).thenReturn(STATS);
        when(statsCalculatorService.calculateChampion(clanrats)).thenReturn(Optional.of(new ChampionDetails("Clawleader", STATS)));

        var pdf = pdfTemplateService.generateArmyPdf(1L);

        var text = text(pdf);
        assertTrue(text.contains("Warhammer Army: Clan Mors"));
        assertTrue(text.contains("Clanrat Squad"));
        assertTrue(text.contains("Clanrat 19"), text);
        assertTrue(text.contains("Clawleader 1"), text);
        assertTrue(text.contains("Ratling Gun Team"));
        assertTrue(text.contains("6+"));
        assertTrue(text.contains("Ratling Gun (55.0 pkt)"));
    }

    @Test
    @DisplayName("generateArmyPdf shows random movement and names detachments after their regiment")
    void generateArmyPdfSpecialUnits() throws IOException {
        var army = army();
        var doomwheel = add(army, "Doomwheel", UnitType.RARE, 1);
        var crew = new UnitProfile();
        crew.setName("Warlock Crew");
        crew.setLd("6");
        doomwheel.getUnit().getProfiles().add(crew);
        var regiment = add(army, "Swordsmen", UnitType.CORE, 20);
        var detachment = add(army, "Handgunners", UnitType.CORE, 5);
        detachment.setParent(regiment);
        owned(army);
        when(statsCalculatorService.calculate(any())).thenReturn(STATS);
        when(statsCalculatorService.calculateChampion(any())).thenReturn(Optional.empty());

        var text = text(pdfTemplateService.generateArmyPdf(1L));

        assertTrue(text.contains("3D6"));
        assertTrue(text.contains("Warlock Crew"), text);
        assertTrue(text.contains("Handgunners Detachment of Swordsmen"));
        assertTrue(text.contains("Swordsmen 20"), text);
        verify(statsCalculatorService).calculate(doomwheel);
    }

    @Test
    @DisplayName("generateArmyPdf of an empty army is still a PDF with every category")
    void generateArmyPdfEmptyArmy() throws IOException {
        owned(army());

        var pdf = pdfTemplateService.generateArmyPdf(1L);

        assertEquals("%PDF", new String(pdf, 0, 4));
        var text = text(pdf);
        for (var unitType : UnitType.values()) {
            assertTrue(text.contains(unitType + ":"));
        }
        verifyNoInteractions(statsCalculatorService);
    }

    @Test
    @DisplayName("generateArmyPdf of a missing army throws NotFoundException")
    void generateArmyPdfNotFound() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenThrow(new NotFoundException("Army with id 1 not found"));

        assertThrows(NotFoundException.class, () -> pdfTemplateService.generateArmyPdf(1L));
        verifyNoInteractions(armyPointsService);
    }

    @Test
    @DisplayName("generateArmyPdf of another user's army throws ForbiddenException")
    void generateArmyPdfForbidden() {
        when(currentUserService.getArmyOwnedByCurrentUser(1L)).thenThrow(new ForbiddenException("Access denied"));

        assertThrows(ForbiddenException.class, () -> pdfTemplateService.generateArmyPdf(1L));
    }
}
