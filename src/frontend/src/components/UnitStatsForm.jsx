const STATS = [
    { key: "m", label: "M" },
    { key: "ws", label: "WS" },
    { key: "bs", label: "BS" },
    { key: "s", label: "S" },
    { key: "t", label: "T" },
    { key: "w", label: "W" },
    { key: "i", label: "I" },
    { key: "a", label: "A" },
    { key: "ld", label: "Ld" },
    { key: "basicSave", label: "Armour save" },
    { key: "wardSave", label: "Ward save" }
];

function UnitStatsForm({ form, setForm }) {
    function update(stat, value) {
        setForm({ ...form, unitStatsRequest: { ...form.unitStatsRequest, [stat]: Number(value) } });
    }

    return (
        <div className="card p-3 mb-3">
            <h4>Stats</h4>
            <small className="text-muted mb-2">Saves: write 5 for 5+, or 0 when the unit has no save.</small>
            <div className="row g-2">
                {STATS.map(stat => (
                    <div key={stat.key} className="col-4 col-md-2 col-xl-1">
                        <label className="form-label small">{stat.label}</label>
                        <input className="form-control" type="number" min="0" max="10" value={form.unitStatsRequest[stat.key]}
                               onChange={e => update(stat.key, e.target.value)} />
                    </div>
                ))}
            </div>
        </div>
    );
}

export default UnitStatsForm;
