import { useState } from "react";

import Modal from "./Modal";

import { changeUsername } from "../api/accountApi";

function ChangeUsernameModal({ show, onClose }) {

    const [newUsername, setNewUsername] = useState("");

    const [password, setPassword] = useState("");

    async function handleSave() {

        try {

            await changeUsername({

                newUsername,
                password

            });

            alert("Username changed successfully.");

            setNewUsername("");

            setPassword("");

            onClose();

        } catch (e) {

            console.error(e);

            alert("Unable to change username.");

        }

    }

    return (

        <Modal

            show={show}

            title="👤 Change Username"

            onClose={onClose}

            onSave={handleSave}

        >

            <div className="mb-3">

                <label className="form-label">

                    New Username

                </label>

                <input

                    type="text"

                    className="form-control"

                    value={newUsername}

                    onChange={e => setNewUsername(e.target.value)}

                    placeholder="Enter new username"

                />

            </div>

            <div>

                <label className="form-label">

                    Current Password

                </label>

                <input

                    type="password"

                    className="form-control"

                    value={password}

                    onChange={e => setPassword(e.target.value)}

                    placeholder="Enter your password"

                />

            </div>

        </Modal>

    );

}

export default ChangeUsernameModal;