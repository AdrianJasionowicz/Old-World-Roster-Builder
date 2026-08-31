import {
    increaseUnitSize,
    decreaseUnitSize
} from "../api/armyApi";


function ArmyList({

    selectedUnits,
    onSelect,
    onDelete,
    onRefresh

}) {

    const handleIncrease = async (e, unitId) => {

        e.stopPropagation();

        try {

            await increaseUnitSize(unitId);
            await onRefresh();

        } catch (e) {

            console.error(e);

        }

    };


    const handleDecrease = async (e, unitId) => {

        e.stopPropagation();

        try {

            await decreaseUnitSize(unitId);
            await onRefresh();

        } catch (e) {

            console.error(e);

        }

    };


    return (

        <div className="army-list">

            <h3 className="mb-3">

                Army

            </h3>

            {

                selectedUnits.length === 0 ?

                    <p>

                        No units selected.

                    </p>

                    :

                    selectedUnits.map(unit => (

                        <div

                            key={unit.id}

                            className="card mb-3 shadow-sm"

                            style={{ cursor: "pointer" }}

                            onClick={() => onSelect(unit.id)}

                        >

                            <div className="card-body">

                                <div className="d-flex justify-content-between align-items-center mb-3">

                                    <h5 className="mb-0">

                                        {unit.unitName}

                                    </h5>

                                    <div className="d-flex align-items-center gap-3">

                                        <span className="badge bg-secondary">

                                            {unit.unitType}

                                        </span>

                                        <strong>

                                            {unit.totalCost} pts

                                        </strong>

                                        <button

                                            className="btn btn-outline-danger btn-sm"

                                            onClick={(e) => {

                                                e.stopPropagation();

                                                onDelete(unit.id);

                                            }}

                                        >

                                            🗑

                                        </button>

                                    </div>

                                </div>


                                <table className="table table-sm table-bordered text-center mb-0">

                                    <thead>

                                    <tr>

                                        <th>M</th>
                                        <th>WS</th>
                                        <th>BS</th>
                                        <th>S</th>
                                        <th>T</th>
                                        <th>W</th>
                                        <th>I</th>
                                        <th>A</th>
                                        <th>LD</th>
                                        <th>Sv</th>
                                        <th>Ward</th>
                                        <th>Qty</th>

                                    </tr>

                                    </thead>


                                    <tbody>

                                    <tr>

                                        <td>{unit.m}</td>
                                        <td>{unit.ws}</td>
                                        <td>{unit.bs}</td>
                                        <td>{unit.s}</td>
                                        <td>{unit.t}</td>
                                        <td>{unit.w}</td>
                                        <td>{unit.i}</td>
                                        <td>{unit.a}</td>
                                        <td>{unit.ld}</td>
                                        <td>{unit.basicSave}+</td>

                                        <td>

                                            {unit.wardSave > 0
                                                ? `${unit.wardSave}+`
                                                : "-"}

                                        </td>


                                        <td>

                                            {(unit.unitType === "CORE" ||
                                                unit.unitType === "SPECIAL") && (

                                                <div className="d-flex justify-content-center align-items-center gap-2">

                                                    <button

                                                        className="btn btn-outline-secondary btn-sm"

                                                        onClick={(e) =>
                                                            handleDecrease(
                                                                e,
                                                                unit.id
                                                            )
                                                        }

                                                    >

                                                        −

                                                    </button>


                                                    <strong>

                                                        {unit.quantity}

                                                    </strong>


                                                    <button

                                                        className="btn btn-outline-secondary btn-sm"

                                                        onClick={(e) =>
                                                            handleIncrease(
                                                                e,
                                                                unit.id
                                                            )
                                                        }

                                                    >

                                                        +

                                                    </button>

                                                </div>

                                            )}


                                            {(unit.unitType === "RARE" ||
                                                unit.unitType === "HERO" ||
                                                unit.unitType === "LORDS") && (

                                                <strong>

                                                    {unit.quantity}

                                                </strong>

                                            )}

                                        </td>

                                    </tr>

                                    </tbody>

                                </table>

                            </div>

                        </div>

                    ))

            }

        </div>

    );

}


export default ArmyList;