import { useState } from "react";

function SpecialRulesForm({ form, setForm }) {

    const [rule, setRule] = useState({

        name: "",
        description: ""

    });

    function addRule() {

        if (!rule.name.trim()) {

            return;

        }

        setForm({

            ...form,

            specialRuleList: [

                ...form.specialRuleList,

                rule

            ]

        });

        setRule({

            name: "",
            description: ""

        });

    }

    function removeRule(index) {

        setForm({

            ...form,

            specialRuleList: form.specialRuleList.filter((_, i) => i !== index)

        });

    }

    return (

        <div className="mt-4">

            <h4>

                Special Rules

            </h4>

            <div className="mb-2">

                <label>

                    Rule Name

                </label>

                <input

                    className="form-control"

                    value={rule.name}

                    onChange={e => setRule({

                        ...rule,

                        name: e.target.value

                    })}

                />

            </div>

            <div className="mb-3">

                <label>

                    Description

                </label>

                <textarea

                    className="form-control"

                    rows={3}

                    value={rule.description}

                    onChange={e => setRule({

                        ...rule,

                        description: e.target.value

                    })}

                />

            </div>

            <button

                className="btn btn-primary"

                onClick={addRule}

            >

                ➕ Add Rule

            </button>

            <hr />

            {

                form.specialRuleList.map((rule, index) => (

                    <div

                        key={index}

                        className="card mt-2"

                    >

                        <div className="card-body">

                            <div className="d-flex justify-content-between">

                                <strong>

                                    {rule.name}

                                </strong>

                                <button

                                    className="btn btn-sm btn-danger"

                                    onClick={() => removeRule(index)}

                                >

                                    Remove

                                </button>

                            </div>

                            <small>

                                {rule.description}

                            </small>

                        </div>

                    </div>

                ))

            }

        </div>

    );

}

export default SpecialRulesForm;