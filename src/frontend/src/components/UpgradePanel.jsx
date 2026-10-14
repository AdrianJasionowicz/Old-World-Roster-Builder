function UpgradePanel({

    upgrades,
    onAddUpgrade,
    onRemoveUpgrade

}) {

    const upgradeTypes = [
        "PICK_UP_GENERAL",
        "FREE_UPGRADE",
        "COMMAND_GROUP",
        "UNIT_EQUIPMENT",
        "MOUNT",
        "WEAPON_TEAM",
        "MAGIC_BANNER",
        "MAGIC_WEAPON",
        "MAGIC_ARMOUR",
        "MAGIC_ITEM",
        "BSB",
        "THE_SCAVENGE_PILE"
    ];

    const upgradeTypeNames = {
        PICK_UP_GENERAL: "GENERAL",
        FREE_UPGRADE: "FREE UPGRADE",
        COMMAND_GROUP: "COMMAND GROUP",
        UNIT_EQUIPMENT: "UNIT EQUIPMENT",
        MOUNT: "MOUNT",
        WEAPON_TEAM: "WEAPON TEAM",
        MAGIC_BANNER: "MAGIC BANNER",
        MAGIC_WEAPON: "MAGIC WEAPON",
        MAGIC_ARMOUR: "MAGIC ARMOUR",
        MAGIC_ITEM: "MAGIC ITEM",
        BSB: "BSB",
        THE_SCAVENGE_PILE: "THE SCAVENGE PILE"
    };

    return (

        <div className="upgrade-panel card shadow-sm">

            <div className="card-header">

                <h4 className="mb-0">

                    Available Upgrades

                </h4>

            </div>

            <div className="card-body">

                {
                    upgrades.length === 0 ?

                        <p className="text-muted">

                            Select unit to see upgrades.

                        </p>

                        :

                        upgradeTypes.map(type => {

                            const typeUpgrades = upgrades.filter(
                                upgrade => upgrade.upgradeType === type
                            );

                            if (typeUpgrades.length === 0) {
                                return null;
                            }

                            return (

                                <div key={type} className="mb-4">

                                    <h6 className="fw-bold border-bottom pb-2">

                                        {upgradeTypeNames[type]}

                                    </h6>

                                    {
                                        typeUpgrades.map(upgrade => (

                                            <div
                                                key={upgrade.id}
                                                className="d-flex justify-content-between align-items-center mb-2"
                                            >

                                                <div>

                                                    <strong>

                                                        {upgrade.name}

                                                    </strong>

                                                    <br />

                                                    <small>

                                                        {upgrade.totalCost} pts

                                                    </small>

                                                </div>

                                                {
                                                    upgrade.selected ?

                                                        <button
                                                            className="btn btn-outline-danger btn-sm"
                                                            onClick={() =>
                                                                onRemoveUpgrade(upgrade.id)
                                                            }
                                                        >

                                                            Remove

                                                        </button>

                                                        :

                                                        <button
                                                            className="btn btn-outline-success btn-sm"
                                                            onClick={() =>
                                                                onAddUpgrade(upgrade.id)
                                                            }
                                                        >

                                                            Add

                                                        </button>
                                                }

                                            </div>

                                        ))
                                    }

                                </div>

                            );

                        })
                }

            </div>

        </div>

    );

}

export default UpgradePanel;