import { useState } from "react";

import { UNIT_TYPES } from "../constants/unitTypes";
import { formatPoints } from "../utils/armyPoints";

function Sidebar({ units = [], onSelectUnit, disabled }) {
    const [search, setSearch] = useState("");

    const filteredUnits = units.filter(unit =>
        unit.name.toLowerCase().includes(search.toLowerCase())
    );

    return (
        <aside className="panel sidebar">
            <h4 className="panel-title">Units</h4>

            <input
                type="text"
                className="form-control mb-3"
                placeholder="Search..."
                value={search}
                onChange={e => setSearch(e.target.value)}
            />

            <div className="unit-list">
                {filteredUnits.length === 0 && <p className="text-muted">No units.</p>}

                {UNIT_TYPES.map(type => {
                    const typeUnits = filteredUnits.filter(unit => unit.unitType === type.value);
                    if (typeUnits.length === 0) {
                        return null;
                    }

                    return (
                        <div key={type.value} className="mb-3">
                            <h6 className="category-label">{type.label}</h6>

                            {typeUnits.map(unit => (
                                <button
                                    key={unit.id}
                                    className="catalog-unit"
                                    disabled={disabled}
                                    onClick={() => onSelectUnit(unit.id)}
                                    title={`Add ${unit.name}`}
                                >
                                    <span>{unit.name}</span>
                                    <span className="text-end">
                                        <strong>{formatPoints(unit.totalCost)}</strong>
                                        <small className="d-block text-muted">
                                            {formatPoints(unit.pointsCostPerUnit)} / model
                                        </small>
                                    </span>
                                </button>
                            ))}
                        </div>
                    );
                })}
            </div>
        </aside>
    );
}

export default Sidebar;
