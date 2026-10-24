import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import { FACTIONS } from "../constants/factions";
import { getUnits, deleteUnit } from "../api/unitApi";

function DeleteUnit() {
    const [faction, setFaction] = useState("SKAVEN");
    const [units, setUnits] = useState([]);
    const [loading, setLoading] = useState(false);
    const [deletingId, setDeletingId] = useState(null);
    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    useEffect(() => {
        async function loadUnits() {
            setLoading(true);
            setError("");
            setMessage("");

            try {
                const response = await getUnits(faction);
                setUnits(response.data);
            } catch {
                setError("Unable to load units.");
            } finally {
                setLoading(false);
            }
        }

        loadUnits();
    }, [faction]);

    async function handleDelete(unit) {
        const confirmed = window.confirm(
            `Are you sure you want to delete "${unit.name}"?`
        );

        if (!confirmed) {
            return;
        }

        setDeletingId(unit.id);
        setError("");
        setMessage("");

        try {
            await deleteUnit(unit.id);

            setUnits(currentUnits =>
                currentUnits.filter(current => current.id !== unit.id)
            );

            setMessage(`Unit "${unit.name}" deleted successfully.`);
        } catch (e) {
            if (e.response?.status === 409) {
                setError(`"${unit.name}" is used in a roster and cannot be deleted.`);
            } else if (e.response?.status === 404) {
                setError("This unit no longer exists.");
            } else if (e.response?.status === 403) {
                setError("You do not have permission to delete this unit.");
            } else {
                setError("Unable to delete unit.");
            }
        } finally {
            setDeletingId(null);
        }
    }

    return (
        <>
            <Navbar />

            <div className="container mt-4">
                <h2>Delete Unit</h2>

                <div className="mb-4">
                    <label htmlFor="faction" className="form-label">
                        Faction
                    </label>

                    <select
                        id="faction"
                        className="form-select"
                        value={faction}
                        onChange={e => setFaction(e.target.value)}
                    >
                        {FACTIONS.map(item => (
                            <option
                                key={item.value}
                                value={item.value}
                            >
                                {item.label}
                            </option>
                        ))}
                    </select>
                </div>

                {error && (
                    <div className="alert alert-danger" role="alert">
                        {error}
                    </div>
                )}

                {message && (
                    <div className="alert alert-success" role="status">
                        {message}
                    </div>
                )}

                {loading ? (
                    <p>Loading units...</p>
                ) : units.length === 0 ? (
                    <div className="alert alert-info">
                        No units found for this faction.
                    </div>
                ) : (
                    <div className="table-responsive">
                        <table className="table table-striped table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Type</th>
                                    <th>Points</th>
                                    <th className="text-end">Actions</th>
                                </tr>
                            </thead>

                            <tbody>
                                {units.map(unit => (
                                    <tr key={unit.id}>
                                        <td>{unit.name}</td>
                                        <td>{unit.unitType}</td>
                                        <td>{unit.pointsCostPerUnit}</td>
                                        <td className="text-end">
                                            <button
                                                className="btn btn-danger btn-sm"
                                                disabled={deletingId !== null}
                                                onClick={() => handleDelete(unit)}
                                            >
                                                {deletingId === unit.id
                                                    ? "Deleting..."
                                                    : "Delete"}
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </>
    );
}

export default DeleteUnit;