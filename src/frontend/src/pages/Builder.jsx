import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import "../styles/builder.css";

import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import ArmyList from "../components/ArmyList";
import UpgradePanel from "../components/UpgradePanel";
import ArmySummary from "../components/ArmySummary";

import { getUnits } from "../api/unitApi";

import {
    getArmy,
    getArmyPoints,
    addUnitToArmy,
    deleteArmyUnit,
    getArmyUnitUpgrades,
    selectUpgrade,
    removeUpgrade
} from "../api/armyApi";

function Builder() {

    const { id } = useParams();

    const [army, setArmy] = useState(null);
    const [units, setUnits] = useState([]);
    const [selectedUnits, setSelectedUnits] = useState([]);
    const [selectedArmyUnitId, setSelectedArmyUnitId] = useState(null);
    const [upgrades, setUpgrades] = useState([]);
    const [summary, setSummary] = useState(null);

    useEffect(() => {

        loadBuilder();

    }, [id]);

    async function loadBuilder() {

        try {

            const armyResponse = await getArmy(id);

            setArmy(armyResponse.data);

            setSelectedUnits(
                (armyResponse.data.armyUnitResponseList ?? [])
                    .sort((a, b) => a.id - b.id)
            );

            const unitsResponse = await getUnits(
                armyResponse.data.faction
            );

            setUnits(unitsResponse.data);

            const summaryResponse = await getArmyPoints(id);

            setSummary(summaryResponse.data);

        } catch (e) {

            console.error(e);

        }

    }

    async function loadUpgrades(armyUnitId) {

        if (armyUnitId === null) {

            setUpgrades([]);

            return;

        }

        try {

            const response =
                await getArmyUnitUpgrades(armyUnitId);

            setUpgrades(response.data);

        } catch (e) {

            console.error(e);

        }

    }

    async function handleSelectUnit(unitId) {

        try {

            await addUnitToArmy(id, unitId);

            await loadBuilder();

        } catch (e) {

            console.error(e);

        }

    }

    async function handleArmyUnitClick(armyUnitId) {

        setSelectedArmyUnitId(armyUnitId);

        await loadUpgrades(armyUnitId);

    }

    async function handleDeleteUnit(armyUnitId) {

        try {

            await deleteArmyUnit(armyUnitId);

            if (selectedArmyUnitId === armyUnitId) {

                setSelectedArmyUnitId(null);

                setUpgrades([]);

            }

            await loadBuilder();

        } catch (e) {

            console.error(e);

        }

    }

    async function handleAddUpgrade(upgradeId) {

        if (selectedArmyUnitId === null) {
            return;
        }

        try {

            await selectUpgrade(
                selectedArmyUnitId,
                upgradeId
            );

            await loadUpgrades(selectedArmyUnitId);
            await loadBuilder();

        } catch (e) {

            console.error(e);

        }

    }

    async function handleRemoveUpgrade(upgradeId) {

        if (selectedArmyUnitId === null) {
            return;
        }

        try {

            await removeUpgrade(
                selectedArmyUnitId,
                upgradeId
            );

            await loadUpgrades(selectedArmyUnitId);
            await loadBuilder();

        } catch (e) {

            console.error(e);

        }

    }

    return (

        <div className="builder-container">

            <Navbar army={army} />

            <div className="builder-grid">

                <Sidebar
                    units={units}
                    onSelectUnit={handleSelectUnit}
                />

                <div className="center-panel">

                    <ArmyList
                        selectedUnits={selectedUnits}
                        selectedArmyUnitId={selectedArmyUnitId}
                        onSelect={handleArmyUnitClick}
                        onDelete={handleDeleteUnit}
                        onRefresh={loadBuilder}
                    />

                </div>

                <div className="right-panel">

                    <UpgradePanel
                        selectedArmyUnitId={selectedArmyUnitId}
                        upgrades={upgrades}
                        onAddUpgrade={handleAddUpgrade}
                        onRemoveUpgrade={handleRemoveUpgrade}
                    />

                    {summary && (

                        <ArmySummary
                            summary={summary}
                        />

                    )}

                </div>

            </div>

        </div>

    );

}

export default Builder;