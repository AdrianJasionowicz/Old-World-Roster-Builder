import "../styles/createArmyModal.css";
import { useState } from "react";

function CreateArmyModal({ show, onClose, onCreate }) {

    const [form, setForm] = useState({

        name: "",
        description: "",
        faction: "EMPIRE",
        pointsLimit: 2000

    });

    function handleChange(e) {

        setForm({

            ...form,

            [e.target.name]: e.target.value

        });

    }

    function submit(e) {

        e.preventDefault();

        onCreate(form);

    }

    if (!show) {

        return null;

    }

    return (

        <div className="modal-backdrop">

            <div className="create-army-modal">

                <h2>

                    ⚔ Create New Roster

                </h2>

                <form onSubmit={submit}>

                    <div className="mb-3">

                        <label>

                            Roster Name

                        </label>

                        <input

                            className="form-control"

                            name="name"

                            value={form.name}

                            onChange={handleChange}

                            required

                        />

                    </div>

                    <div className="mb-3">

                        <label>

                            Description

                        </label>

                        <textarea

                            className="form-control"

                            name="description"

                            rows="3"

                            value={form.description}

                            onChange={handleChange}

                        />

                    </div>

                    <div className="mb-3">

                        <label>

                            Faction

                        </label>

                        <select

                            className="form-select"

                            name="faction"

                            value={form.faction}

                            onChange={handleChange}

                        >

                            <option value="EMPIRE">Empire</option>
                            <option value="DWARFS">Dwarfs</option>
                            <option value="HIGH_ELVES">High Elves</option>
                            <option value="WOOD_ELVES">Wood Elves</option>
                            <option value="BRETONNIA">Bretonnia</option>
                            <option value="WARRIORS_OF_CHAOS">Warriors of Chaos</option>
                            <option value="BEASTMEN">Beastmen</option>
                            <option value="ORCS_AND_GOBLINS">Orcs & Goblins</option>
                            <option value="VAMPIRE_COUNTS">Vampire Counts</option>
                            <option value="TOMB_KINGS">Tomb Kings</option>
                            <option value="SKAVEN">Skaven</option>
                            <option value="DARK_ELVES">Dark Elves</option>
                            <option value="LIZARDMEN">Lizardmen</option>
                            <option value="OGRE_KINGDOMS">Ogre Kingdoms</option>
                            <option value="CHAOS_DWARFS">Chaos Dwarfs</option>
                            <option value="DAEMONS_OF_CHAOS">Daemons of Chaos</option>

                        </select>

                    </div>
                    
<div className="mb-4">

    <label>

        Points Limit

    </label>

    <input

        type="number"

        className="form-control"

        name="pointsLimit"

        value={form.pointsLimit}

        min="100"

        max="10000"

        step="1"

        onChange={handleChange}

        required

    />

</div>

                    <div className="d-flex justify-content-end gap-2">

                        <button

                            type="button"

                            className="btn btn-secondary"

                            onClick={onClose}

                        >

                            Cancel

                        </button>

                        <button

                            className="btn btn-success"

                            type="submit"

                        >

                            Create

                        </button>

                    </div>

                </form>

            </div>

        </div>

    );

}

export default CreateArmyModal;