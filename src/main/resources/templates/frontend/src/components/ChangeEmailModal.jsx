import { useState } from "react";

import Modal from "./Modal";

import { changeEmail } from "../api/accountApi";

function ChangeEmailModal({ show, onClose }) {

    const [newEmail, setNewEmail] = useState("");

    const [password, setPassword] = useState("");

    async function handleSave() {

        try {

            await changeEmail({

                newEmail,
                password

            });

            alert("Email changed successfully.");

            setNewEmail("");

            setPassword("");

            onClose();

        } catch (e) {

            console.error(e);

            alert("Unable to change email.");

        }

    }

    return (

        <Modal

            show={show}

            title="📧 Change Email"

            onClose={onClose}

            onSave={handleSave}

        >

            <div className="mb-3">

                <label className="form-label">

                    New Email

                </label>

                <input

                    type="email"

                    className="form-control"

                    value={newEmail}

                    onChange={e => setNewEmail(e.target.value)}

                    placeholder="Enter new email"

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

export default ChangeEmailModal;