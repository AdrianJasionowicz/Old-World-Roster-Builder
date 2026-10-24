import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "../components/Navbar";
import CreateArmyModal from "../components/CreateArmyModal";
import EditArmyModal from "../components/EditArmyModal";
import ErrorAlert from "../components/ErrorAlert";

import { errorMessage } from "../api/api";
import { changePointsLimit, createArmy, deleteArmy, getArmies, renameArmy } from "../api/armyApi";
import { FACTIONS } from "../constants/factions";
import { useSubmit } from "../hooks/useSubmit";
import { formatPoints } from "../utils/armyPoints";

import "../styles/rosterMenu.css";

function factionLabel(faction) {
    return FACTIONS.find(f => f.value === faction)?.label ?? faction;
}

function usedPoints(roster) {
    return roster.armyUnitResponseList.reduce((sum, unit) => sum + unit.totalCost, 0);
}

function RosterMenu() {
    const navigate = useNavigate();

    const [rosters, setRosters] = useState([]);
    const [error, setError] = useState("");
    const [showCreate, setShowCreate] = useState(false);
    const [editedRoster, setEditedRoster] = useState(null);
    const createForm = useSubmit();
    const editForm = useSubmit();

    const loadArmies = useCallback(() =>
        getArmies()
            .then(response => setRosters(response.data))
            .catch(e => setError(errorMessage(e, "Could not load your rosters."))), []);

    useEffect(() => {
        getArmies()
            .then(response => setRosters(response.data))
            .catch(e => setError(errorMessage(e, "Could not load your rosters.")));
    }, []);

    async function handleCreate(request) {
        let createdId = null;
        const created = await createForm.submit(async () => {
            const response = await createArmy(request);
            createdId = response.data.id;
        });
        if (created) {
            navigate(`/builder/${createdId}`);
        }
    }

    async function handleEdit({ name, pointsLimit }) {
        const roster = editedRoster;
        const saved = await editForm.submit(async () => {
            if (name !== roster.name) {
                await renameArmy(roster.id, name);
            }
            if (pointsLimit !== roster.pointsLimit) {
                await changePointsLimit(roster.id, pointsLimit);
            }
        });
        if (saved) {
            setEditedRoster(null);
            await loadArmies();
        }
    }

    async function handleDelete(roster) {
        if (!window.confirm(`Delete roster "${roster.name}"?`)) {
            return;
        }
        try {
            await deleteArmy(roster.id);
            await loadArmies();
        } catch (e) {
            setError(errorMessage(e));
        }
    }

    return (
        <div className="page">
            <Navbar />

            <div className="roster-container">
                <div className="roster-header">
                    <h1>Your Rosters</h1>
                    <button className="btn btn-primary btn-lg" onClick={() => setShowCreate(true)}>
                        <i className="bi bi-plus-lg me-2" />
                        New Roster
                    </button>
                </div>

                <ErrorAlert message={error} onClose={() => setError("")} />

                {rosters.length === 0 ? (
                    <div className="roster-empty-state">
                        <h3>No rosters yet</h3>
                        <p className="text-muted">Create your first army list to get started.</p>
                    </div>
                ) : (
                    <div className="roster-grid">
                        {rosters.map(roster => {
                            const used = usedPoints(roster);
                            const over = used > roster.pointsLimit;
                            return (
                                <div key={roster.id} className="roster-card" onClick={() => navigate(`/builder/${roster.id}`)}>
                                    <div>
                                        <span className="badge text-bg-dark mb-2">{factionLabel(roster.faction)}</span>
                                        <h3>{roster.name}</h3>
                                        {roster.description && <p>{roster.description}</p>}
                                        <h4 className={over ? "text-danger" : ""}>
                                            {formatPoints(used)} / {formatPoints(roster.pointsLimit)} pts
                                        </h4>
                                        <small className="text-muted">{roster.armyUnitResponseList.length} units</small>
                                    </div>

                                    <div className="roster-buttons" onClick={e => e.stopPropagation()}>
                                        <button className="btn btn-success" onClick={() => navigate(`/builder/${roster.id}`)}>Open</button>
                                        <button className="btn btn-outline-secondary" onClick={() => setEditedRoster(roster)}>Edit</button>
                                        <button className="btn btn-outline-danger" onClick={() => handleDelete(roster)}>Delete</button>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                )}
            </div>

            <CreateArmyModal
                show={showCreate}
                onClose={() => setShowCreate(false)}
                onCreate={handleCreate}
                saving={createForm.saving}
                error={createForm.error}
            />

            {editedRoster && (
                <EditArmyModal
                    army={editedRoster}
                    onClose={() => setEditedRoster(null)}
                    onSave={handleEdit}
                    saving={editForm.saving}
                    error={editForm.error}
                />
            )}
        </div>
    );
}

export default RosterMenu;
