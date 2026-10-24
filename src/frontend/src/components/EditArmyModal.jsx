import { useState } from "react";

import Modal from "./Modal";

function EditArmyModal({ army, onClose, onSave, saving, error }) {
    const [name, setName] = useState(army.name);
    const [pointsLimit, setPointsLimit] = useState(army.pointsLimit);

    return (
        <Modal
            show
            title="Edit Roster"
            onClose={onClose}
            onSave={() => onSave({ name, pointsLimit: Number(pointsLimit) })}
            saving={saving}
            error={error}
        >
            <div className="mb-3">
                <label className="form-label">Roster name</label>
                <input className="form-control" maxLength={50} value={name} onChange={e => setName(e.target.value)} required autoFocus />
            </div>
            <div>
                <label className="form-label">Points limit</label>
                <input type="number" className="form-control" min="1" step="1" value={pointsLimit} onChange={e => setPointsLimit(e.target.value)} required />
            </div>
        </Modal>
    );
}

export default EditArmyModal;
