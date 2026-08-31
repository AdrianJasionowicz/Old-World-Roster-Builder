package com.armybuilderv2.armyBuilderV2.pdfExport;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.army.ArmyPointsService;
import com.armybuilderv2.armyBuilderV2.army.ArmyRepository;
import com.armybuilderv2.armyBuilderV2.army.ArmyService;
import com.armybuilderv2.armyBuilderV2.army.model.ArmyPointsView;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import com.armybuilderv2.armyBuilderV2.selectedUpgrade.SelectedUpgrade;
import com.armybuilderv2.armyBuilderV2.unit.UnitType;
import com.armybuilderv2.armyBuilderV2.unitStats.StatsCalculatorService;
import com.armybuilderv2.armyBuilderV2.unitStats.model.UnitDetails;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.armybuilderv2.armyBuilderV2.unit.UnitType.*;

@Service
public class PdfTemplateService {

    private final ArmyService armyService;
    private final ArmyRepository armyRepository;
    private final StatsCalculatorService statsCalculatorService;
    private final ArmyPointsService armyPointsService;

    public PdfTemplateService(
            ArmyService armyService,
            ArmyRepository armyRepository,
            StatsCalculatorService statsCalculatorService,
            ArmyPointsService armyPointsService) {

        this.armyService = armyService;
        this.armyRepository = armyRepository;
        this.statsCalculatorService = statsCalculatorService;
        this.armyPointsService = armyPointsService;
    }

    public byte[] generateArmyPdf(String armyName, Long armyId) {

        ArmyPointsView armyPointsView =
                armyPointsService.calculateArmyPoints(armyId);

        Army army =
                armyRepository.getReferenceById(armyId);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            createHeader(document, army, armyPointsView);

            List<ArmyUnit> armyUnitList =
                    army.getArmyUnitsList();

            UnitType[] unitTypes = {
                    LORDS,
                    HERO,
                    CORE,
                    SPECIAL,
                    RARE
            };

            for (UnitType unitType : unitTypes) {

                List<ArmyUnit> units =
                        getUnitsByType(armyUnitList, unitType);

                createUnitTypeSection(
                        document,
                        unitType,
                        units,
                        armyPointsView
                );
            }

            document.add(
                    createUpgradeDescriptionsTable(armyUnitList)
            );

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Błąd przy generowaniu PDF: " + e.getMessage(),
                    e
            );
        }
    }

    private List<ArmyUnit> getUnitsByType(
            List<ArmyUnit> armyUnits,
            UnitType unitType) {

        return armyUnits.stream()
                .filter(armyUnit ->
                        armyUnit.getUnit()
                                .getUnitType()
                                .equals(unitType))
                .toList();
    }

    private void createUnitTypeSection(
            Document document,
            UnitType unitType,
            List<ArmyUnit> armyUnits,
            ArmyPointsView armyPointsView) {

        document.add(
                createUnitTypeHeader(
                        unitType,
                        armyPointsView
                )
        );

        for (ArmyUnit armyUnit : armyUnits) {

            document.add(
                    createUnitHeader(armyUnit)
            );

            document.add(
                    createStatsTable(armyUnit)
            );

            document.add(
                    new Paragraph("Upgrades:")
                            .setFontSize(10)
                            .setMarginTop(10)
            );

            document.add(
                    createSelectedUpgradesTable(armyUnit)
            );
        }
    }

    private Table createUnitTypeHeader(
            UnitType unitType,
            ArmyPointsView armyPointsView) {

        Table table = new Table(1)
                .setWidth(UnitValue.createPercentValue(100));

        Cell cell = new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);

        Paragraph paragraph = new Paragraph();

        paragraph.addTabStops(
                new TabStop(520, TabAlignment.RIGHT)
        );

        paragraph.add(unitType + ": ")
                .setBold();

        paragraph.add(new Tab());

        paragraph.add(
                getUsedPoints(unitType, armyPointsView) + " pts"
        );

        cell.add(paragraph);
        table.addCell(cell);

        return table;
    }

    private double getUsedPoints(
            UnitType unitType,
            ArmyPointsView armyPointsView) {

        return switch (unitType) {

            case LORDS -> armyPointsView.lordsPointsView().usedLords();

            case HERO -> armyPointsView.heroesPointsView().usedHeroes();

            case CORE -> armyPointsView.corePointsView().usedCore();

            case SPECIAL -> armyPointsView.specialPointsView().usedSpecial();

            case RARE -> armyPointsView.rarePointsView().usedRare();
        };
    }

    private Table createUnitHeader(ArmyUnit armyUnit) {

        Table table = new Table(1)
                .setWidth(UnitValue.createPercentValue(100));

        Paragraph paragraph = new Paragraph();

        paragraph.addTabStops(
                new TabStop(1000, TabAlignment.RIGHT)
        );

        paragraph.add(
                armyUnit.getUnit().getName() + " Squad"
        );

        paragraph.add(new Tab());

        paragraph.add(
                armyUnit.getTotalCost() + " pts"
        );

        Cell cell = new Cell()
                .add(paragraph);

        table.addCell(cell);

        return table;
    }

    private Table createStatsTable(ArmyUnit armyUnit) {

        UnitDetails unitDetails =
                statsCalculatorService.getUnitDetails(
                        armyUnit.getId()
                );

        Table statsTable = new Table(13)
                .setWidth(UnitValue.createPercentValue(100));

        addStatsHeaders(statsTable);

        addUnitStats(
                statsTable,
                armyUnit,
                unitDetails
        );

        return statsTable;
    }

    private void addStatsHeaders(Table statsTable) {

        String[] headers = {
                "Unit",
                "Qty",
                "M",
                "Ws",
                "Bs",
                "S",
                "T",
                "W",
                "I",
                "A",
                "Ld",
                "Save",
                "Ward Save"
        };

        for (String header : headers) {

            statsTable.addCell(
                    new Cell()
                            .add(new Paragraph(header))
                            .setBackgroundColor(
                                    ColorConstants.LIGHT_GRAY
                            )
                            .setTextAlignment(
                                    TextAlignment.CENTER
                            )
            );
        }
    }

    private void addUnitStats(
            Table statsTable,
            ArmyUnit armyUnit,
            UnitDetails unitDetails) {

        addStatCell(
                statsTable,
                armyUnit.getUnit().getName()
        );

        addStatCell(
                statsTable,
                String.valueOf(
                        (int) Math.round(
                                armyUnit.getQuantity()
                        )
                )
        );

        if (isRandomMovementUnit(armyUnit)) {

            addStatCell(statsTable, "3D6");

        } else {

            addStatCell(
                    statsTable,
                    String.valueOf(unitDetails.m())
            );
        }

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.ws())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.bs())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.s())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.t())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.w())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.i())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.a())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.ld())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.basicSave())
        );

        addStatCell(
                statsTable,
                String.valueOf(unitDetails.wardSave())
        );
    }

    private void addStatCell(
            Table table,
            String value) {

        table.addCell(
                new Cell()
                        .add(new Paragraph(value))
                        .setTextAlignment(
                                TextAlignment.CENTER
                        )
        );
    }

    private boolean isRandomMovementUnit(
            ArmyUnit armyUnit) {

        String unitName =
                armyUnit.getUnit().getName();

        return unitName.equalsIgnoreCase(
                "Hell-pit Abomination"
        ) || unitName.equalsIgnoreCase(
                "Doomwheel"
        );
    }

    private Table createSelectedUpgradesTable(
            ArmyUnit armyUnit) {

        Table upgradesTable = new Table(3)
                .setWidth(UnitValue.createPercentValue(100));

        List<SelectedUpgrade> selectedUpgrades =
                armyUnit.getSelectedUpgradesList();

        for (int i = 0; i < selectedUpgrades.size(); i += 3) {

            addUpgradeCell(
                    upgradesTable,
                    selectedUpgrades,
                    i
            );

            addUpgradeCell(
                    upgradesTable,
                    selectedUpgrades,
                    i + 1
            );

            addUpgradeCell(
                    upgradesTable,
                    selectedUpgrades,
                    i + 2
            );
        }

        return upgradesTable;
    }

    private void addUpgradeCell(
            Table table,
            List<SelectedUpgrade> upgrades,
            int index) {

        if (index >= upgrades.size()) {

            table.addCell(
                    new Cell()
                            .setBorder(Border.NO_BORDER)
            );

            return;
        }

        SelectedUpgrade selectedUpgrade =
                upgrades.get(index);

        double totalCost =
                selectedUpgrade.getUpgrade().getPointsCost()
                        * selectedUpgrade.getQuantity();

        Cell cell = new Cell()
                .add(
                        new Paragraph(
                                selectedUpgrade
                                        .getUpgrade()
                                        .getName()
                                        + " ("
                                        + totalCost
                                        + " pkt)"
                        )
                )
                .setBorder(Border.NO_BORDER);

        table.addCell(cell);
    }

    private void createHeader(
            Document document,
            Army army,
            ArmyPointsView armyPointsView) {

        document.add(
                new Paragraph(
                        "Warhammer Army: "
                                + army.getName()
                )
                        .setBold()
                        .setFontSize(14)
                        .setTextAlignment(
                                TextAlignment.CENTER
                        )
        );

        document.add(
                new Paragraph(
                        "Army: "
                                + army.getFaction()
                                + " "
                                + armyPointsView.usedPoints()
                                + " / "
                                + armyPointsView.pointsLimit()
                                + " pts"
                )
                        .setBold()
                        .setFontSize(10)
                        .setTextAlignment(
                                TextAlignment.CENTER
                        )
        );

        document.add(
                new Paragraph()
                        .setBold()
                        .setFontSize(10)
                        .setTextAlignment(
                                TextAlignment.CENTER
                        )
        );
    }

    private Table createUpgradeDescriptionsTable(
            List<ArmyUnit> armyUnitList) {

        Table upgradesTable = new Table(2)
                .setWidth(UnitValue.createPercentValue(100));

        Set<String> added = new HashSet<>();

        armyUnitList.stream()
                .flatMap(
                        armyUnit ->
                                armyUnit
                                        .getSelectedUpgradesList()
                                        .stream()
                )
                .map(SelectedUpgrade::getUpgrade)
                .filter(
                        upgrade ->
                                added.add(upgrade.getName())
                )
                .forEach(upgrade -> {

                    upgradesTable.addCell(
                            new Cell()
                                    .add(
                                            new Paragraph(
                                                    upgrade.getName()
                                            )
                                    )
                    );

                    upgradesTable.addCell(
                            new Cell()
                                    .add(
                                            new Paragraph(
                                                    upgrade.getDescription()
                                            )
                                    )
                    );
                });

        return upgradesTable;
    }
}