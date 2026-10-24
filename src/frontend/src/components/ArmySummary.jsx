import { UNIT_TYPES } from "../constants/unitTypes";
import { categoryPoints, formatPoints } from "../utils/armyPoints";

function ArmySummary({ summary }) {
    if (!summary) {
        return null;
    }

    const overLimit = summary.usedPoints > summary.pointsLimit;
    const usedPercent = Math.min(100, (summary.usedPoints / summary.pointsLimit) * 100);

    return (
        <div className="panel army-summary">
            <h4 className="panel-title">Army Summary</h4>

            <div className={`validity ${summary.valid ? "valid" : "invalid"}`}>
                <i className={`bi ${summary.valid ? "bi-check-circle-fill" : "bi-exclamation-triangle-fill"} me-2`} />
                {summary.valid ? "Army list is valid" : "Army list is invalid"}
            </div>

            <div className="d-flex justify-content-between mt-3">
                <strong>Total</strong>
                <span className={overLimit ? "text-danger fw-bold" : ""}>
                    {formatPoints(summary.usedPoints)} / {formatPoints(summary.pointsLimit)} pts
                </span>
            </div>
            <div className="progress mb-3" role="progressbar" aria-valuenow={usedPercent} aria-valuemin="0" aria-valuemax="100">
                <div className={`progress-bar ${overLimit ? "bg-danger" : "bg-success"}`} style={{ width: `${usedPercent}%` }} />
            </div>

            <table className="table table-sm mb-2">
                <tbody>
                    {UNIT_TYPES.map(type => {
                        const points = categoryPoints(summary, type.value);
                        return (
                            <tr key={type.value} className={points.valid ? "" : "table-danger"}>
                                <td className="text-start">{type.label}</td>
                                <td className="text-end">
                                    {formatPoints(points.used)} / {points.isMinimum ? "min " : ""}{formatPoints(points.limit)}
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            <small className="text-muted d-block mb-2">Lords and heroes share one characters limit.</small>

            {summary.violations?.length > 0 && (
                <ul className="violations">
                    {summary.violations.map(violation => <li key={violation}>{violation}</li>)}
                </ul>
            )}
        </div>
    );
}

export default ArmySummary;
