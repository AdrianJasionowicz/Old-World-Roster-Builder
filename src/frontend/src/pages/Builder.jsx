import { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import "../styles/builder.css";

import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import ArmyList from "../components/ArmyList";
import ArmyRules from "../components/ArmyRules";
import UpgradePanel from "../components/UpgradePanel";
import ArmySummary from "../components/ArmySummary";
import ErrorAlert from "../components/ErrorAlert";

import { errorMessage } from "../api/api";
import { rosterAsText } from "../utils/rosterText";
import { getUnits } from "../api/unitApi";
import {
    addDetachment,
    addUnitToArmy,
    decreaseUnitSize,
    deleteArmyUnit,
    getArmy,
    getArmyPoints,
    getArmyUnitUpgrades,
    increaseUnitSize,
    removeUpgrade,
    selectUpgrade
} from "../api/armyApi";

function Builder() {
    const { id } = useParams();

    const [army, setArmy] = useState(null);
    const [catalog, setCatalog] = useState([]);
    const [summary, setSummary] = useState(null);
    const [selectedArmyUnitId, setSelectedArmyUnitId] = useState(null);
    const [upgrades, setUpgrades] = useState([]);
    const [error, setError] = useState("");
    const [busy, setBusy] = useState(false);
    const [copied, setCopied] = useState(false);

    const reloadArmy = useCallback(async () => {
        const [armyResponse, pointsResponse] = await Promise.all([getArmy(id), getArmyPoints(id)]);
        setArmy(armyResponse.data);
        setSummary(pointsResponse.data);
        return armyResponse.data;
    }, [id]);

    useEffect(() => {
        async function load() {
            try {
                const loadedArmy = await reloadArmy();
                const unitsResponse = await getUnits(loadedArmy.faction);
                setCatalog(unitsResponse.data);
            } catch (e) {
                setError(errorMessage(e, "Could not load the roster."));
            }
        }

        load();
    }, [reloadArmy]);

    async function change(action, unitWithOpenOptions = selectedArmyUnitId) {
        setBusy(true);
        setError("");
        try {
            await action();
        } catch (e) {
            setError(errorMessage(e));
        } finally {
            try {
                await reloadArmy();
                if (unitWithOpenOptions !== null) {
                    const response = await getArmyUnitUpgrades(unitWithOpenOptions);
                    setUpgrades(response.data);
                }
            } catch {
                setUpgrades([]);
            }
            setBusy(false);
        }
    }

    async function handleSelectArmyUnit(armyUnitId) {
        setSelectedArmyUnitId(armyUnitId);
        try {
            const response = await getArmyUnitUpgrades(armyUnitId);
            setUpgrades(response.data);
        } catch (e) {
            setError(errorMessage(e));
        }
    }

    function handleDeleteUnit(armyUnitId) {
        const deletingSelected = armyUnitId === selectedArmyUnitId || selectedUnit?.parentId === armyUnitId;
        if (deletingSelected) {
            setSelectedArmyUnitId(null);
            setUpgrades([]);
        }
        change(() => deleteArmyUnit(armyUnitId), deletingSelected ? null : selectedArmyUnitId);
    }

    function handleResize(armyUnitId, delta) {
        change(() => delta > 0 ? increaseUnitSize(armyUnitId) : decreaseUnitSize(armyUnitId));
    }

    async function handleCopy() {
        try {
            await navigator.clipboard.writeText(rosterAsText(army, summary));
            setCopied(true);
            setTimeout(() => setCopied(false), 2000);
        } catch {
            setError("Could not copy to the clipboard.");
        }
    }

    const units = [...(army?.armyUnitResponseList ?? [])].sort((a, b) => a.id - b.id);
    const selectedUnit = units.find(unit => unit.id === selectedArmyUnitId) ?? null;

    return (
        <div className="builder-container">
            <Navbar army={army} />

            <div className="builder-grid">
                <Sidebar
                    units={catalog}
                    disabled={busy}
                    onSelectUnit={unitId => change(() => addUnitToArmy(id, unitId))}
                />

                <main className="center-panel">
                    <div className="roster-toolbar">
                        <button className="btn btn-sm btn-outline-secondary" onClick={handleCopy} disabled={!army || !summary}>
                            <i className={`bi ${copied ? "bi-check2" : "bi-clipboard"} me-1`} />
                            {copied ? "Copied" : "Copy as text"}
                        </button>
                    </div>
                    <ErrorAlert message={error} onClose={() => setError("")} />
                    <ArmyRules rules={army?.armyRules} />
                    <ArmyList
                        units={units}
                        summary={summary}
                        selectedArmyUnitId={selectedArmyUnitId}
                        onSelect={handleSelectArmyUnit}
                        onDelete={handleDeleteUnit}
                        onResize={handleResize}
                        disabled={busy}
                    />
                </main>

                <div className="right-panel">
                    <UpgradePanel
                        unit={selectedUnit}
                        upgrades={upgrades}
                        catalog={catalog}
                        disabled={busy}
                        onAddUpgrade={upgradeId => change(() => selectUpgrade(selectedArmyUnitId, upgradeId))}
                        onRemoveUpgrade={upgradeId => change(() => removeUpgrade(selectedArmyUnitId, upgradeId))}
                        onAddDetachment={unitId => change(() => addDetachment(selectedArmyUnitId, unitId))}
                    />
                    <ArmySummary summary={summary} />
                </div>
            </div>
        </div>
    );
}

export default Builder;
