import { useState } from "react";

import { UPGRADE_TYPES } from "../constants/upgradeTypes";

const EMPTY_UPGRADE = { name: "", pointsCost: 0, upgradeType: "COMMAND_GROUP", description: "" };

function typeLabel(value) {
    return UPGRADE_TYPES.find(type => type.value === value)?.label ?? value;
}

function UpgradesForm({ form, setForm }) {
    const [upgrade, setUpgrade] = useState(EMPTY_UPGRADE);

    function addUpgrade() {
        if (!upgrade.name.trim()) {
            return;
        }
        setForm({ ...form, upgradesList: [...form.upgradesList, upgrade] });
        setUpgrade(EMPTY_UPGRADE);
    }

    function removeUpgrade(index) {
        setForm({ ...form, upgradesList: form.upgradesList.filter((_, i) => i !== index) });
    }

    return (
        <div className="card p-3 mb-3">
            <h4>Upgrades</h4>
            <small className="text-muted mb-2">Free upgrades are always given to the unit. Equipment is paid per model.</small>

            <div className="row g-2 align-items-end">
                <div className="col-md-3">
                    <label className="form-label">Name</label>
                    <input className="form-control" value={upgrade.name} onChange={e => setUpgrade({ ...upgrade, name: e.target.value })} />
                </div>
                <div className="col-md-2">
                    <label className="form-label">Points</label>
                    <input className="form-control" type="number" min="0" step="0.5" value={upgrade.pointsCost}
                           onChange={e => setUpgrade({ ...upgrade, pointsCost: Number(e.target.value) })} />
                </div>
                <div className="col-md-3">
                    <label className="form-label">Type</label>
                    <select className="form-select" value={upgrade.upgradeType} onChange={e => setUpgrade({ ...upgrade, upgradeType: e.target.value })}>
                        {UPGRADE_TYPES.map(type => <option key={type.value} value={type.value}>{type.label}</option>)}
                    </select>
                </div>
                <div className="col-md-2">
                    <label className="form-label">Description</label>
                    <input className="form-control" value={upgrade.description} onChange={e => setUpgrade({ ...upgrade, description: e.target.value })} />
                </div>
                <div className="col-md-2">
                    <button type="button" className="btn btn-outline-primary w-100" onClick={addUpgrade}>Add upgrade</button>
                </div>
            </div>

            {form.upgradesList.length > 0 && (
                <ul className="list-group mt-3">
                    {form.upgradesList.map((item, index) => (
                        <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                            <span>
                                <strong>{item.name}</strong>
                                <span className="badge text-bg-secondary ms-2">{typeLabel(item.upgradeType)}</span>
                                <span className="ms-2">{item.pointsCost} pts</span>
                                {item.description && <small className="d-block text-muted">{item.description}</small>}
                            </span>
                            <button type="button" className="btn btn-sm btn-outline-danger" onClick={() => removeUpgrade(index)}>Remove</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default UpgradesForm;
