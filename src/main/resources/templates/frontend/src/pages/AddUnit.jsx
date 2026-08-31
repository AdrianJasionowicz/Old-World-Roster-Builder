import { useState } from "react";

import Navbar from "../components/Navbar";
import UnitStatsForm from "../components/UnitStatsForm";
import SpecialRulesForm from "../components/SpecialRulesForm";
import UpgradesForm from "../components/UpgradesForm";

import { FACTIONS } from "../constants/factions";
import { UNIT_TYPES } from "../constants/unitTypes";

import { addUnit } from "../api/adminApi";

function AddUnit() {

    const [form, setForm] = useState({

        name: "",

        pointsCostPerUnit: 0,

        minQuantity: 0,

        unitType: "CORE",

        unitFaction: "EMPIRE",

        specialRuleList: [],

        upgradesList: [],

        unitStatsRequest: {

            m: 0,
            ws: 0,
            bs: 0,
            s: 0,
            t: 0,
            w: 0,
            i: 0,
            a: 0,
            ld: 0,
            basicSave: 0,
            wardSave: 0

        }

    });

    async function save() {

        try {

            await addUnit(form);

            alert("Unit added.");

            console.log(form);

        } catch (e) {

            console.error(e);

            alert("Unable to add unit.");

        }

    }

    return (

        <>

            <Navbar />

            <div className="container mt-4">

                <h2>

                    Add Unit

                </h2>

                <div className="mb-3">

                    <label>

                        Name

                    </label>

                    <input

                        className="form-control"

                        value={form.name}

                        onChange={e => setForm({

                            ...form,

                            name: e.target.value

                        })}

                    />

                </div>

                <div className="row">

                    <div className="col">

                        <label>

                            Points

                        </label>

                        <input

                            className="form-control"

                            type="number"

                            value={form.pointsCostPerUnit}

                            onChange={e => setForm({

                                ...form,

                                pointsCostPerUnit: Number(e.target.value)

                            })}

                        />

                    </div>

                    <div className="col">

                        <label>

                            Minimum Quantity

                        </label>

                        <input

                            className="form-control"

                            type="number"

                            value={form.minQuantity}

                            onChange={e => setForm({

                                ...form,

                                minQuantity: Number(e.target.value)

                            })}

                        />

                    </div>

                </div>

                <div className="row mt-3">

                    <div className="col">

                        <label>

                            Faction

                        </label>

                        <select

                            className="form-select"

                            value={form.unitFaction}

                            onChange={e => setForm({

                                ...form,

                                unitFaction: e.target.value

                            })}

                        >

                            {

                                FACTIONS.map(faction => (

                                    <option

                                        key={faction.value}

                                        value={faction.value}

                                    >

                                        {faction.label}

                                    </option>

                                ))

                            }

                        </select>

                    </div>

                    <div className="col">

                        <label>

                            Unit Type

                        </label>

                        <select

                            className="form-select"

                            value={form.unitType}

                            onChange={e => setForm({

                                ...form,

                                unitType: e.target.value

                            })}

                        >

                            {

                                UNIT_TYPES.map(type => (

                                    <option

                                        key={type.value}

                                        value={type.value}

                                    >

                                        {type.label}

                                    </option>

                                ))

                            }

                        </select>

                    </div>

                </div>

                <UnitStatsForm

                    form={form}

                    setForm={setForm}

                />

                <SpecialRulesForm

                    form={form}

                    setForm={setForm}

                />

                <UpgradesForm

                    form={form}

                    setForm={setForm}

                />

                <button

                    className="btn btn-success mt-4"

                    onClick={save}

                >

                    Save Unit

                </button>

            </div>

        </>

    );

}

export default AddUnit;