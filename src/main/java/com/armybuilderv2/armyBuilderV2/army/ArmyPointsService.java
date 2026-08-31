package com.armybuilderv2.armyBuilderV2.army;

import com.armybuilderv2.armyBuilderV2.army.model.*;
import com.armybuilderv2.armyBuilderV2.armyUnit.ArmyUnit;
import org.springframework.stereotype.Service;

@Service
public class ArmyPointsService {

    private ArmyService armyService;

    public ArmyPointsService(ArmyService armyService) {
        this.armyService = armyService;
    }

    public ArmyPointsView calculateArmyPoints(Long armyId) {
        Army army = armyService.getArmyOwnedByCurrentUser(armyId);
        double pointsLimit = army.getPointsLimit();
        double lordsAndHeroesAndSpecialLimit = pointsLimit * 0.5;
        double coreAndRare = pointsLimit * 0.25;
        double usedLords = 0.0;
        double usedCore = 0.0;
        double usedRare = 0.0;
        double usedSpecial = 0.0;
        double usedHeroes = 0.0;

        for (ArmyUnit armyUnit : army.getArmyUnitsList()) {
            switch (armyUnit.getUnit().getUnitType()) {
                case CORE -> usedCore += armyUnit.getTotalCost();
                case RARE -> usedRare += armyUnit.getTotalCost();
                case SPECIAL -> usedSpecial += armyUnit.getTotalCost();
                case LORDS -> usedLords += armyUnit.getTotalCost();
                case HERO -> usedHeroes += armyUnit.getTotalCost();
            }
        }
        double totalSpendPoints = usedLords + usedCore + usedRare + usedSpecial + usedHeroes;

        boolean coreValid = usedCore >= coreAndRare;
        CorePointsView corePointsView = new CorePointsView(usedCore, coreAndRare, coreValid);
        boolean specialValid = usedSpecial <= lordsAndHeroesAndSpecialLimit;
        SpecialPointsView specialPointsView = new SpecialPointsView(usedSpecial, lordsAndHeroesAndSpecialLimit, specialValid);
        boolean rareValid = usedRare <= coreAndRare;
        RarePointsView rarePointsView = new RarePointsView(usedRare, coreAndRare, rareValid);
        boolean lordsValid = usedLords <= lordsAndHeroesAndSpecialLimit;
        LordsPointsView lordsPointsView = new LordsPointsView(usedLords, lordsAndHeroesAndSpecialLimit, lordsValid);
        boolean heroesValid = usedHeroes <= lordsAndHeroesAndSpecialLimit;
        HeroesPointsView heroesPointsView = new HeroesPointsView(usedHeroes, lordsAndHeroesAndSpecialLimit, heroesValid);
        boolean arePointsValid = totalSpendPoints <= pointsLimit;

        return new ArmyPointsView(totalSpendPoints,pointsLimit,lordsPointsView,heroesPointsView,corePointsView,specialPointsView,rarePointsView,arePointsValid);
    }


}
