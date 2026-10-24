import { useEffect, useState } from "react";

import Modal from "./Modal";
import { getFactionsWithUnits } from "../api/unitApi";
import { FACTIONS } from "../constants/factions";

const EMPTY_FORM = { name: "", description: "", faction: "SKAVEN", pointsLimit: 2000 };

function CreateArmyModal({ show, onClose, onCreate, saving, error }) {
    const [form, setForm] = useState(EMPTY_FORM);
    const [playableFactions, setPlayableFactions] = useState(null);

    useEffect(() => {
        if (show) {
            getFactionsWithUnits().then(response => setPlayableFactions(response.data)).catch(() => {});
        }
    }, [show]);

    function update(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    const isPlayable = faction => playableFactions === null || playableFactions.includes(faction);

    return (
        <Modal
            show={show}
            title="Create New Roster"
            onClose={onClose}
            onSave={() => onCreate({ ...form, pointsLimit: Number(form.pointsLimit) })}
            saveLabel="Create"
            saveVariant="success"
            saving={saving}
            error={error}
        >
            <div className="mb-3">
                <label className="form-label">Roster name</label>
                <input className="form-control" name="name" maxLength={50} value={form.name} onChange={update} required autoFocus />
            </div>

            <div className="mb-3">
                <label className="form-label">Description</label>
                <textarea className="form-control" name="description" rows="2" maxLength={500} value={form.description} onChange={update} />
            </div>

            <div className="row">
                <div className="col-7">
                    <label className="form-label">Faction</label>
                    <select className="form-select" name="faction" value={form.faction} onChange={update}>
                        {FACTIONS.map(faction => (
                            <option key={faction.value} value={faction.value} disabled={!isPlayable(faction.value)}>
                                {faction.label}{isPlayable(faction.value) ? "" : " (no units yet)"}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="col-5">
                    <label className="form-label">Points limit</label>
                    <input type="number" className="form-control" name="pointsLimit" min="1" step="1" value={form.pointsLimit} onChange={update} required />
                </div>
            </div>
        </Modal>
    );
}

export default CreateArmyModal;
