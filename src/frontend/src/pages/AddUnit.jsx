import { useState } from "react";

import Navbar from "../components/Navbar";
import ErrorAlert from "../components/ErrorAlert";
import UnitStatsForm from "../components/UnitStatsForm";
import SpecialRulesForm from "../components/SpecialRulesForm";
import UpgradesForm from "../components/UpgradesForm";

import { addUnit } from "../api/adminApi";
import { FACTIONS } from "../constants/factions";
import { UNIT_TYPES } from "../constants/unitTypes";
import { useSubmit } from "../hooks/useSubmit";

const EMPTY_UNIT = {
    name: "",
    pointsCostPerUnit: 1,
    minQuantity: 1,
    unitType: "CORE",
    unitFaction: "SKAVEN",
    specialRuleList: [],
    upgradesList: [],
    unitStatsRequest: { m: 0, ws: 0, bs: 0, s: 0, t: 0, w: 0, i: 0, a: 0, ld: 0, basicSave: 0, wardSave: 0 }
};

function AddUnit() {
    const [form, setForm] = useState(EMPTY_UNIT);
    const [savedName, setSavedName] = useState("");
    const { submit, saving, error } = useSubmit();

    function update(field, value) {
        setForm({ ...form, [field]: value });
    }

    async function save(e) {
        e.preventDefault();
        if (await submit(() => addUnit(form))) {
            setSavedName(form.name);
            setForm(EMPTY_UNIT);
        }
    }

    return (
        <div className="page">
            <Navbar />

            <form className="container my-4" onSubmit={save}>
                <h2>Add Unit</h2>

                <ErrorAlert message={error} />
                {savedName && <div className="alert alert-success">Unit "{savedName}" added.</div>}

                <div className="card p-3 mb-3">
                    <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input className="form-control" value={form.name} onChange={e => update("name", e.target.value)} required />
                    </div>

                    <div className="row g-3">
                        <div className="col-md-3">
                            <label className="form-label">Points per model</label>
                            <input className="form-control" type="number" min="0.5" step="0.5" value={form.pointsCostPerUnit}
                                   onChange={e => update("pointsCostPerUnit", Number(e.target.value))} required />
                        </div>
                        <div className="col-md-3">
                            <label className="form-label">Minimum size</label>
                            <input className="form-control" type="number" min="1" value={form.minQuantity}
                                   onChange={e => update("minQuantity", Number(e.target.value))} required />
                        </div>
                        <div className="col-md-3">
                            <label className="form-label">Faction</label>
                            <select className="form-select" value={form.unitFaction} onChange={e => update("unitFaction", e.target.value)}>
                                {FACTIONS.map(faction => <option key={faction.value} value={faction.value}>{faction.label}</option>)}
                            </select>
                        </div>
                        <div className="col-md-3">
                            <label className="form-label">Category</label>
                            <select className="form-select" value={form.unitType} onChange={e => update("unitType", e.target.value)}>
                                {UNIT_TYPES.map(type => <option key={type.value} value={type.value}>{type.label}</option>)}
                            </select>
                        </div>
                    </div>
                </div>

                <UnitStatsForm form={form} setForm={setForm} />
                <SpecialRulesForm form={form} setForm={setForm} />
                <UpgradesForm form={form} setForm={setForm} />

                <button type="submit" className="btn btn-success mt-3" disabled={saving}>
                    {saving ? "Saving..." : "Save unit"}
                </button>
            </form>
        </div>
    );
}

export default AddUnit;
