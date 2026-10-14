import { useState } from "react";

function Sidebar({

    units = [],
    onSelectUnit

}) {

    const [search, setSearch] = useState("");

    const filteredUnits = units.filter(unit =>
        unit.name.toLowerCase().includes(search.toLowerCase())
    );

    const unitTypes = [
        "LORDS",
        "HERO",
        "CORE",
        "SPECIAL",
        "RARE"
    ];

    return (

        <aside className="sidebar">

            <h4 className="mb-4">

                Available Units

            </h4>

            <input
                type="text"
                className="form-control mb-3"
                placeholder="Search..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            <div className="unit-list">

                {
                    filteredUnits.length === 0 ?

                        <p className="text-muted">

                            No units.

                        </p>

                        :

                        unitTypes.map(type => {

                            const typeUnits = filteredUnits.filter(
                                unit => unit.unitType === type
                            );

                            if (typeUnits.length === 0) {
                                return null;
                            }

                            return (

                                <div key={type} className="mb-4">

                                    <h6 className="fw-bold border-bottom pb-2">

                                        {type}

                                    </h6>

                                    {
                                        typeUnits.map(unit => (

                                            <button
                                                key={unit.id}
                                                className="btn btn-outline-secondary unit-button mb-2"
                                                onClick={() => onSelectUnit(unit.id)}
                                            >

                                                <div className="d-flex justify-content-between w-100">

                                                    <span>

                                                        {unit.name}

                                                    </span>

                                                    <span className="text-end">

                                                        <strong>

                                                            {unit.totalCost} pts

                                                        </strong>

                                                        <br />

                                                        <small className="text-muted">

                                                            {unit.pointsCostPerUnit} pts / model

                                                        </small>

                                                    </span>

                                                </div>

                                            </button>

                                        ))
                                    }

                                </div>

                            );

                        })
                }

            </div>

        </aside>

    );

}

export default Sidebar;