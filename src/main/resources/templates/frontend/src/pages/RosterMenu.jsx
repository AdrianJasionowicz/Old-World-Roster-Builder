import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Navbar from "../components/Navbar";
import CreateArmyModal from "../components/CreateArmyModal";

import {
    getArmies,
    deleteArmy,
    createArmy
} from "../api/armyApi";

import "../styles/rosterMenu.css";

function RosterMenu() {

    const navigate = useNavigate();

    const [rosters, setRosters] = useState([]);

    const [showModal, setShowModal] = useState(false);

    useEffect(() => {

        loadArmies();

    }, []);

    async function loadArmies() {

        try {

            const response = await getArmies();

            console.log("ARMIES:", response.data);

            setRosters(response.data);

        } catch (e) {

            console.error("LOAD ERROR:", e);

        }

    }

    function openRoster(id) {

        navigate(`/builder/${id}`);

    }

    function createRoster() {

        setShowModal(true);

    }

    async function handleCreateArmy(request) {

        try {

            const response = await createArmy(request);

            console.log("CREATED:", response.data);

            setShowModal(false);

            navigate(`/builder/${response.data.id}`);

        } catch (e) {

            console.error("CREATE ERROR:", e);

        }

    }

    function editRoster(id) {

        alert(`Rename roster ${id} - coming soon`);

    }

    async function removeRoster(id) {

        console.log("DELETE CLICK:", id);

        if (!window.confirm("Delete roster?")) {

            return;

        }

        try {

            const response = await deleteArmy(id);

            console.log("DELETE RESPONSE:", response);

            console.log("STATUS:", response.status);

            await loadArmies();

        } catch (e) {

            console.error("DELETE ERROR:", e);

        }

    }

    return (

        <div className="builder-container">

            <Navbar/>

            <div className="roster-container">

                <div className="roster-header">

                    <h1>⚔ Old World Roster Builder</h1>

                    <p>Select a roster or create a new one.</p>

                </div>

                <div className="roster-grid">

                    {

                        rosters.length === 0 ?

                            <h3>No rosters found.</h3>

                            :

                            rosters.map(roster => (

                                <div
                                    key={roster.id}
                                    className="roster-card">

                                    <div>

                                        <h3>{roster.name}</h3>

                                        <p>{roster.faction}</p>

                                        <p>{roster.description}</p>

                                        <h4>{roster.pointsLimit} pts</h4>

                                    </div>

                                    <div className="roster-buttons">

                                        <button
                                            className="btn btn-success"
                                            onClick={() => openRoster(roster.id)}>

                                            Open

                                        </button>

                                        <button
                                            className="btn btn-warning"
                                            onClick={() => editRoster(roster.id)}>

                                            Rename

                                        </button>

                                        <button
                                            className="btn btn-danger"
                                            onClick={() => removeRoster(roster.id)}>

                                            Delete

                                        </button>

                                    </div>

                                </div>

                            ))

                    }

                </div>

                <div className="create-roster">

                    <button
                        className="btn btn-primary btn-lg"
                        onClick={createRoster}>

                        ➕ Create New Roster

                    </button>

                </div>

            </div>

            <CreateArmyModal
                show={showModal}
                onClose={() => setShowModal(false)}
                onCreate={handleCreateArmy}
            />

        </div>

    );

}

export default RosterMenu;