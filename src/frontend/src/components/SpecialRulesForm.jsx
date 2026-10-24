import { useState } from "react";

const EMPTY_RULE = { name: "", description: "" };

function SpecialRulesForm({ form, setForm }) {
    const [rule, setRule] = useState(EMPTY_RULE);

    function addRule() {
        if (!rule.name.trim()) {
            return;
        }
        setForm({ ...form, specialRuleList: [...form.specialRuleList, rule] });
        setRule(EMPTY_RULE);
    }

    function removeRule(index) {
        setForm({ ...form, specialRuleList: form.specialRuleList.filter((_, i) => i !== index) });
    }

    return (
        <div className="card p-3 mb-3">
            <h4>Special Rules</h4>

            <div className="row g-2 align-items-end">
                <div className="col-md-4">
                    <label className="form-label">Name</label>
                    <input className="form-control" value={rule.name} onChange={e => setRule({ ...rule, name: e.target.value })} />
                </div>
                <div className="col-md-6">
                    <label className="form-label">Description</label>
                    <input className="form-control" value={rule.description} onChange={e => setRule({ ...rule, description: e.target.value })} />
                </div>
                <div className="col-md-2">
                    <button type="button" className="btn btn-outline-primary w-100" onClick={addRule}>Add rule</button>
                </div>
            </div>

            {form.specialRuleList.length > 0 && (
                <ul className="list-group mt-3">
                    {form.specialRuleList.map((item, index) => (
                        <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                            <span><strong>{item.name}</strong> <small className="text-muted">{item.description}</small></span>
                            <button type="button" className="btn btn-sm btn-outline-danger" onClick={() => removeRule(index)}>Remove</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default SpecialRulesForm;
