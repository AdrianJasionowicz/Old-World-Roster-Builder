function UnitStatsForm({ form, setForm }) {

    function update(field, value) {

        setForm({

            ...form,

            unitStatsRequest: {

                ...form.unitStatsRequest,

                [field]: value

            }

        });

    }

    return (

        <>

            <h4 className="mt-4">

                Unit Stats

            </h4>

            <div className="row">

                {

                    ["m","ws","bs","s","t","w","i","a","ld","basicSave","wardSave"].map(stat => (

                        <div
                            className="col-2 mb-3"
                            key={stat}
                        >

                            <label>

                                {stat.toUpperCase()}

                            </label>

                            <input

                                className="form-control"

                                type="number"

                                value={form.unitStatsRequest[stat]}

                                onChange={e => update(stat, e.target.value)}

                            />

                        </div>

                    ))

                }

            </div>

        </>

    );

}

export default UnitStatsForm;