import { UNIT_TYPES } from "../constants/unitTypes";
import { FREE_UPGRADE } from "../constants/upgradeTypes";
import { categoryPoints, formatPoints } from "../utils/armyPoints";
import { withDetachments } from "../utils/detachments";

const STATS = ["m", "ws", "bs", "s", "t", "w", "i", "a", "ld"];

function formatSave(save) {
    return save == null ? "-" : `${save}+`;
}

function ArmyList({ units, summary, selectedArmyUnitId, onSelect, onDelete, onResize, disabled }) {
    if (units.length === 0) {
        return (
            <div className="panel roster-empty">
                <h4>Your roster is empty</h4>
                <p className="text-muted mb-0">Pick a unit from the list on the left to add it.</p>
            </div>
        );
    }

    return (
        <div className="army-list">
            {UNIT_TYPES.map(type => {
                const typeUnits = units.filter(unit => unit.unitType === type.value);
                const points = categoryPoints(summary, type.value);
                if (typeUnits.length === 0 && !(points?.isMinimum)) {
                    return null;
                }

                return (
                    <section key={type.value} className="roster-category">
                        <header className={`category-header ${points && !points.valid ? "invalid" : ""}`}>
                            <span>{type.label}</span>
                            {points && (
                                <span>
                                    {formatPoints(points.used)} / {points.isMinimum ? "min " : ""}{formatPoints(points.limit)} pts
                                </span>
                            )}
                        </header>

                        {withDetachments(typeUnits).map(unit => (
                            <RosterUnit
                                key={unit.id}
                                unit={unit}
                                parentName={units.find(parent => parent.id === unit.parentId)?.unitName}
                                selected={unit.id === selectedArmyUnitId}
                                onSelect={onSelect}
                                onDelete={onDelete}
                                onResize={onResize}
                                disabled={disabled}
                            />
                        ))}
                    </section>
                );
            })}
        </div>
    );
}

function RosterUnit({ unit, parentName, selected, onSelect, onDelete, onResize, disabled }) {
    const resizable = unit.resizable;
    const options = unit.selectedUpgrades.filter(upgrade => upgrade.upgradeType !== FREE_UPGRADE);

    function stop(handler) {
        return e => {
            e.stopPropagation();
            handler();
        };
    }

    return (
        <article
            className={["roster-unit", selected && "selected", unit.parentId != null && "detachment"].filter(Boolean).join(" ")}
            onClick={() => onSelect(unit.id)}
        >
            {parentName && <div className="detachment-label">Detachment of {parentName}</div>}
            <div className="d-flex justify-content-between align-items-center gap-2">
                <div className="d-flex align-items-center gap-2">
                    {resizable ? (
                        <div className="btn-group btn-group-sm">
                            <button className="btn btn-outline-secondary" disabled={disabled} onClick={stop(() => onResize(unit.id, -1))}>−</button>
                            <span className="btn btn-outline-secondary disabled quantity">{unit.quantity}</span>
                            <button className="btn btn-outline-secondary" disabled={disabled} onClick={stop(() => onResize(unit.id, 1))}>+</button>
                        </div>
                    ) : (
                        <span className="quantity-fixed">{unit.quantity}</span>
                    )}
                    <h5 className="mb-0">{unit.unitName}</h5>
                </div>

                <div className="d-flex align-items-center gap-2">
                    <strong>{formatPoints(unit.totalCost)} pts</strong>
                    <button
                        className="btn btn-sm btn-outline-danger"
                        disabled={disabled}
                        title="Remove unit"
                        onClick={stop(() => onDelete(unit.id))}
                    >
                        <i className="bi bi-trash" />
                    </button>
                </div>
            </div>

            {options.length > 0 && (
                <p className="unit-options">{options.map(option => option.name).join(", ")}</p>
            )}

            {unit.specialRules.length > 0 && (
                <p className="unit-rules">
                    {unit.specialRules.map(rule => (
                        <span key={rule.name} className="unit-rule" title={rule.description ?? ""}>{rule.name}</span>
                    ))}
                </p>
            )}

            <table className="table table-sm table-bordered stat-line mb-0 mt-2">
                <thead>
                    <tr>
                        <th className="stat-name" />
                        {STATS.map(stat => <th key={stat}>{stat.toUpperCase()}</th>)}
                        <th>Sv</th>
                        <th>Ward</th>
                    </tr>
                </thead>
                <tbody>
                    <StatRow name={unit.unitName} stats={unit} />
                    {unit.champion && <StatRow name={unit.champion.name} stats={unit.champion.stats} />}
                    {unit.profiles.map((profile, index) => (
                        <StatRow key={index} name={profile.name} stats={profile} />
                    ))}
                </tbody>
            </table>
        </article>
    );
}

function StatRow({ name, stats }) {
    return (
        <tr>
            <td className="stat-name">{name}</td>
            {STATS.map(stat => <td key={stat}>{stats[stat] ?? "-"}</td>)}
            <td>{formatSave(stats.basicSave)}</td>
            <td>{formatSave(stats.wardSave)}</td>
        </tr>
    );
}

export default ArmyList;
