package com.oldworld.rosterbuilder.pdfExport;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyPointsService;
import com.oldworld.rosterbuilder.army.model.ArmyPointsView;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.loginUser.CurrentUserService;
import com.oldworld.rosterbuilder.selectedUpgrade.SelectedUpgrade;
import com.oldworld.rosterbuilder.unit.UnitType;
import com.oldworld.rosterbuilder.unitStats.StatsCalculatorService;
import com.oldworld.rosterbuilder.unitStats.model.UnitDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
class PdfTemplateService {

    private static final String[] STATS_HEADERS = {"Unit", "Qty", "M", "Ws", "Bs", "S", "T", "W", "I", "A", "Ld", "Save", "Ward Save"};
    private static final Set<String> RANDOM_MOVEMENT_UNITS = Set.of("hell-pit abomination", "doomwheel");
    private static final int UPGRADE_COLUMNS = 3;

    private final CurrentUserService currentUserService;
    private final StatsCalculatorService statsCalculatorService;
    private final ArmyPointsService armyPointsService;

    byte[] generateArmyPdf(Long armyId) {
        var army = currentUserService.getArmyOwnedByCurrentUser(armyId);
        var armyPoints = armyPointsService.calculateArmyPoints(army);
        var usedPointsByType = armyPointsService.usedPointsByType(army);

        try (var output = new ByteArrayOutputStream()) {
            var document = new Document(new PdfDocument(new PdfWriter(output)));
            addHeader(document, army, armyPoints);
            for (var unitType : UnitType.values()) {
                addUnitTypeSection(document, unitType, unitsOfType(army, unitType), usedPointsByType.get(unitType));
            }
            document.add(createUpgradeDescriptionsTable(army.getArmyUnitsList()));
            document.close();
            log.info("Generated PDF for army {}", armyId);
            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not generate PDF for army " + armyId, e);
        }
    }

    private List<ArmyUnit> unitsOfType(Army army, UnitType unitType) {
        return army.getArmyUnitsList().stream()
                .filter(armyUnit -> armyUnit.getUnit().getUnitType() == unitType)
                .toList();
    }

    private void addHeader(Document document, Army army, ArmyPointsView armyPoints) {
        document.add(new Paragraph("Warhammer Army: " + army.getName())
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Army: " + army.getFaction() + " " + armyPoints.usedPoints() + " / " + armyPoints.pointsLimit() + " pts")
                .setBold()
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
    }

    private void addUnitTypeSection(Document document, UnitType unitType, List<ArmyUnit> armyUnits, double usedPoints) {
        document.add(createUnitTypeHeader(unitType, usedPoints));
        for (var armyUnit : armyUnits) {
            document.add(createUnitHeader(armyUnit));
            document.add(createStatsTable(armyUnit));
            document.add(new Paragraph("Upgrades:").setFontSize(10).setMarginTop(10));
            document.add(createSelectedUpgradesTable(armyUnit.getSelectedUpgradesList()));
        }
    }

    private Table createUnitTypeHeader(UnitType unitType, double usedPoints) {
        var paragraph = new Paragraph().addTabStops(new TabStop(520, TabAlignment.RIGHT));
        paragraph.add(unitType + ": ").setBold();
        paragraph.add(new Tab());
        paragraph.add(usedPoints + " pts");
        var table = new Table(1).setWidth(UnitValue.createPercentValue(100));
        table.addCell(new Cell().setBackgroundColor(ColorConstants.LIGHT_GRAY).add(paragraph));
        return table;
    }

    private Table createUnitHeader(ArmyUnit armyUnit) {
        var paragraph = new Paragraph().addTabStops(new TabStop(1000, TabAlignment.RIGHT));
        paragraph.add(armyUnit.isDetachment()
                ? armyUnit.getUnit().getName() + " Detachment of " + armyUnit.getParent().getUnit().getName()
                : armyUnit.getUnit().getName() + " Squad");
        paragraph.add(new Tab());
        paragraph.add(armyUnit.getTotalCost() + " pts");
        var table = new Table(1).setWidth(UnitValue.createPercentValue(100));
        table.addCell(new Cell().add(paragraph));
        return table;
    }

    private Table createStatsTable(ArmyUnit armyUnit) {
        var stats = statsCalculatorService.calculate(armyUnit);
        var table = new Table(STATS_HEADERS.length).setWidth(UnitValue.createPercentValue(100));
        for (var header : STATS_HEADERS) {
            table.addCell(new Cell()
                    .add(new Paragraph(header))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER));
        }
        for (var value : statValues(armyUnit, stats)) {
            table.addCell(new Cell().add(new Paragraph(value)).setTextAlignment(TextAlignment.CENTER));
        }
        return table;
    }

    private List<String> statValues(ArmyUnit armyUnit, UnitDetails stats) {
        var movement = RANDOM_MOVEMENT_UNITS.contains(armyUnit.getUnit().getName().toLowerCase()) ? "3D6" : String.valueOf(stats.m());
        return List.of(
                armyUnit.getUnit().getName(),
                String.valueOf(armyUnit.getQuantity()),
                movement,
                String.valueOf(stats.ws()),
                String.valueOf(stats.bs()),
                String.valueOf(stats.s()),
                String.valueOf(stats.t()),
                String.valueOf(stats.w()),
                String.valueOf(stats.i()),
                String.valueOf(stats.a()),
                String.valueOf(stats.ld()),
                formatSave(stats.basicSave()),
                formatSave(stats.wardSave()));
    }

    private String formatSave(Integer save) {
        return save == null ? "-" : save + "+";
    }

    private Table createSelectedUpgradesTable(List<SelectedUpgrade> selectedUpgrades) {
        var table = new Table(UPGRADE_COLUMNS).setWidth(UnitValue.createPercentValue(100));
        var cellCount = (selectedUpgrades.size() + UPGRADE_COLUMNS - 1) / UPGRADE_COLUMNS * UPGRADE_COLUMNS;
        for (var i = 0; i < cellCount; i++) {
            var cell = new Cell().setBorder(Border.NO_BORDER);
            if (i < selectedUpgrades.size()) {
                var selectedUpgrade = selectedUpgrades.get(i);
                cell.add(new Paragraph(selectedUpgrade.getUpgrade().getName() + " (" + selectedUpgrade.getTotalCost() + " pkt)"));
            }
            table.addCell(cell);
        }
        return table;
    }

    private Table createUpgradeDescriptionsTable(List<ArmyUnit> armyUnits) {
        var table = new Table(2).setWidth(UnitValue.createPercentValue(100));
        var alreadyListed = new HashSet<String>();
        armyUnits.stream()
                .flatMap(armyUnit -> armyUnit.getSelectedUpgradesList().stream())
                .map(SelectedUpgrade::getUpgrade)
                .filter(upgrade -> alreadyListed.add(upgrade.getName()))
                .forEach(upgrade -> {
                    table.addCell(new Cell().add(new Paragraph(upgrade.getName())));
                    table.addCell(new Cell().add(new Paragraph(upgrade.getDescription() == null ? "" : upgrade.getDescription())));
                });
        return table;
    }
}
