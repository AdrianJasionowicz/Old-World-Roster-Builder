import { useState } from "react";

import { FREE_UPGRADE, MAGIC_ITEM_TYPES, RUNE_LABELS, UPGRADE_TYPES } from "../constants/upgradeTypes";
import { formatPoints } from "../utils/armyPoints";

function UpgradePanel({ unit, upgrades, catalog, onAddUpgrade, onRemoveUpgrade, onAddDetachment, disabled }) {
    if (!unit) {
        return (
            <div className="panel upgrade-panel">
                <h4 className="panel-title">Options</h4>
                <p className="text-muted">Click a unit in your roster to see its options.</p>
            </div>
        );
    }

    const freeUpgrades = upgrades.filter(upgrade => upgrade.upgradeType === FREE_UPGRADE);

    return (
        <div className="panel upgrade-panel">
            <h4 className="panel-title mb-2">{unit.unitName}</h4>

            <MagicItemsBudget unit={unit} upgrades={upgrades} />

            {unit.canHaveDetachments && (
                <DetachmentPicker
                    key={unit.id}
                    catalog={catalog}
                    disabled={disabled}
                    onAdd={onAddDetachment}
                />
            )}

            {upgrades.length === 0 && <p className="text-muted">This unit has no options.</p>}

            {UPGRADE_TYPES.filter(type => type.value !== FREE_UPGRADE).map(type => {
                const typeUpgrades = upgrades.filter(upgrade => upgrade.upgradeType === type.value);
                if (typeUpgrades.length === 0) {
                    return null;
                }

                return (
                    <div key={type.value} className="mb-3">
                        <h6 className="category-label">{type.label}</h6>
                        {typeUpgrades.map(upgrade => (
                            <UpgradeOption
                                key={upgrade.id}
                                upgrade={upgrade}
                                disabled={disabled}
                                onToggle={() => upgrade.selected ? onRemoveUpgrade(upgrade.id) : onAddUpgrade(upgrade.id)}
                            />
                        ))}
                    </div>
                );
            })}

            {freeUpgrades.length > 0 && (
                <div>
                    <h6 className="category-label">Special Rules & Wargear</h6>
                    <ul className="free-upgrades">
                        {freeUpgrades.map(upgrade => (
                            <li key={upgrade.id} title={upgrade.description}>{upgrade.name}</li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

function UpgradeOption({ upgrade, disabled, onToggle }) {
    const blocked = !upgrade.selected && upgrade.allowed === false;
    const classes = ["upgrade-option", upgrade.selected && "selected", blocked && "blocked"].filter(Boolean).join(" ");

    return (
        <label className={classes} title={blocked ? upgrade.notAllowedReason : upgrade.description}>
            <input
                type="checkbox"
                className="form-check-input me-2"
                checked={upgrade.selected}
                disabled={disabled || blocked}
                onChange={onToggle}
            />
            <span className="flex-grow-1">
                {upgrade.name}
                {upgrade.rune && (
                    <span className={`badge rune-badge ${upgrade.rune === "MASTER_RUNE" ? "text-bg-warning" : "text-bg-secondary"}`}>
                        {RUNE_LABELS[upgrade.rune]}
                    </span>
                )}
                {blocked ? (
                    <small className="d-block text-danger">{upgrade.notAllowedReason}</small>
                ) : (
                    upgrade.description && upgrade.description !== upgrade.name && (
                        <small className="d-block text-muted">{upgrade.description}</small>
                    )
                )}
            </span>
            <span className="text-nowrap">{formatPoints(upgrade.totalCost)} pts</span>
        </label>
    );
}

function DetachmentPicker({ catalog, disabled, onAdd }) {
    const candidates = catalog.filter(catalogUnit => catalogUnit.canBeDetachment);
    const [chosenId, setChosenId] = useState(null);

    if (candidates.length === 0) {
        return null;
    }
    const unitId = chosenId ?? candidates[0].id;

    return (
        <div className="mb-3">
            <h6 className="category-label">Detachments</h6>
            <p className="small text-muted mb-2">5 models minimum, at most half the size of this regiment, up to 2 per regiment.</p>
            <div className="detachment-picker">
                <select className="form-select form-select-sm" value={unitId} onChange={e => setChosenId(Number(e.target.value))}>
                    {candidates.map(candidate => (
                        <option key={candidate.id} value={candidate.id}>
                            {candidate.name} ({formatPoints(candidate.pointsCostPerUnit)} pts/model)
                        </option>
                    ))}
                </select>
                <button className="btn btn-sm btn-outline-primary text-nowrap" disabled={disabled} onClick={() => onAdd(unitId)}>
                    <i className="bi bi-plus-lg me-1" />Add
                </button>
            </div>
        </div>
    );
}

function MagicItemsBudget({ unit, upgrades }) {
    const limit = unit.magicItemsPointsLimit;
    if (!limit || !upgrades.some(upgrade => MAGIC_ITEM_TYPES.includes(upgrade.upgradeType))) {
        return null;
    }

    const spent = upgrades
        .filter(upgrade => upgrade.selected && MAGIC_ITEM_TYPES.includes(upgrade.upgradeType))
        .reduce((sum, upgrade) => sum + upgrade.totalCost, 0);

    return (
        <div className="magic-budget mb-3">
            <div className="d-flex justify-content-between small">
                <span>Magic items</span>
                <span className={spent > limit ? "text-danger fw-bold" : ""}>{formatPoints(spent)} / {limit} pts</span>
            </div>
            <div className="progress" style={{ height: 6 }}>
                <div className="progress-bar bg-info" style={{ width: `${Math.min(100, (spent / limit) * 100)}%` }} />
            </div>
        </div>
    );
}

export default UpgradePanel;
