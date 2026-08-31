function ArmySummary({ summary }) {

    return (

        <div className="army-summary card shadow-sm">

            <div className="card-header">

                <h4 className="mb-0">

                    Army Summary

                </h4>

            </div>

            <div className="card-body">

                <div
                    className={`text-center fw-bold mb-3 ${
                        summary.valid
                            ? "text-success"
                            : "text-danger"
                    }`}
                >

                    {summary.valid ? (
                        <>
                            ✓ Army list is valid
                        </>
                    ) : (
                        <>
                            ✗ Army list is invalid
                        </>
                    )}

                </div>

                <table className="table table-sm">

                    <tbody>

                        <tr>

                            <td><strong>Total</strong></td>

                            <td className="text-end">

                                {summary.usedPoints} / {summary.pointsLimit}

                            </td>

                        </tr>

                        <tr>

                            <td>Lords</td>

                            <td className="text-end">

                                {summary.lordsPointsView.usedLords} / {summary.lordsPointsView.availableLords}

                            </td>

                        </tr>

                        <tr>

                            <td>Heroes</td>

                            <td className="text-end">

                                {summary.heroesPointsView.usedHeroes} / {summary.heroesPointsView.availableHeroes}

                            </td>

                        </tr>

                        <tr>

                            <td>Core</td>

                            <td className="text-end">

                                {summary.corePointsView.usedCore} / {summary.corePointsView.minimalCore}

                            </td>

                        </tr>

                        <tr>

                            <td>Special</td>

                            <td className="text-end">

                                {summary.specialPointsView.usedSpecial} / {summary.specialPointsView.availableSpecial}

                            </td>

                        </tr>

                        <tr>

                            <td>Rare</td>

                            <td className="text-end">

                                {summary.rarePointsView.usedRare} / {summary.rarePointsView.availableRare}

                            </td>

                        </tr>

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default ArmySummary;