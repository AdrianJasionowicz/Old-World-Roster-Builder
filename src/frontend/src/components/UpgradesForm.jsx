import { useState } from "react";

import { UPGRADE_TYPES } from "../constants/upgradeTypes";

function UpgradesForm({ form, setForm }) {

    const [upgrade, setUpgrade] = useState({

        name: "",
        pointsCost: 0,
        upgradeType: "COMMAND_GROUP",
        description: ""

    });

    function addUpgrade() {

        if (!upgrade.name.trim()) {

            return;

        }

        setForm({

            ...form,

            upgradesList: [

                ...form.upgradesList,

                upgrade

            ]

        });

        setUpgrade({

            name: "",
            pointsCost: 0,
            upgradeType: "COMMAND_GROUP",
            description: ""

        });

    }

    function removeUpgrade(index) {

        setForm({

            ...form,

            upgradesList: form.upgradesList.filter((_, i) => i !== index)

        });

    }

    return (

        <div className="mt-4">

            <h4>

                Upgrades

            </h4>

            <div className="mb-2">

                <label>

                    Upgrade Name

                </label>

                <input

                    className="form-control"

                    value={upgrade.name}

                    onChange={e => setUpgrade({

                        ...upgrade,

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

                        value={upgrade.pointsCost}

                        onChange={e => setUpgrade({

                            ...upgrade,

                            pointsCost: Number(e.target.value)

                        })}

                    />

                </div>

                <div className="col">

                    <label>

                        Upgrade Type

                    </label>

                    <select

                        className="form-select"

                        value={upgrade.upgradeType}

                        onChange={e => setUpgrade({

                            ...upgrade,

                            upgradeType: e.target.value

                        })}

                    >

                        {

                            UPGRADE_TYPES.map(type => (

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

            <div className="mt-3">

                <label>

                    Description

                </label>

                <textarea

                    className="form-control"

                    rows={3}

                    value={upgrade.description}

                    onChange={e => setUpgrade({

                        ...upgrade,

                        description: e.target.value

                    })}

                />

            </div>

            <button

                className="btn btn-primary mt-3"

                onClick={addUpgrade}

            >

                ➕ Add Upgrade

            </button>

            <hr />

            {

                form.upgradesList.map((upgrade, index) => (

                    <div

                        key={index}

                        className="card mt-2"

                    >

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <strong>

                                    {upgrade.name}

                                </strong>

                                <button

                                    className="btn btn-sm btn-danger"

                                    onClick={() => removeUpgrade(index)}

                                >

                                    Remove

                                </button>

                            </div>

                            <div>

                                <strong>

                                    {upgrade.pointsCost} pts

                                </strong>

                            </div>

                            <div>

                                {upgrade.upgradeType}

                            </div>

                            <small>

                                {upgrade.description}

                            </small>

                        </div>

                    </div>

                ))

            }

        </div>

    );

}

export default UpgradesForm;