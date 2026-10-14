import { useState } from "react";

import Modal from "./Modal";

import { changePassword } from "../api/accountApi";

function ChangePasswordModal({ show, onClose }) {

    const [password, setPassword] = useState("");

    const [newPassword, setNewPassword] = useState("");

    async function handleSave() {

        try {

            await changePassword({

                password,
                newPassword

            });

            alert("Password changed successfully.");

            setPassword("");

            setNewPassword("");

            onClose();

        } catch (e) {

            console.error(e);

            alert("Unable to change password.");

        }

    }

    return (

        <Modal

            show={show}

            title="🔒 Change Password"

            onClose={onClose}

            onSave={handleSave}

        >

            <div className="mb-3">

                <label className="form-label">

                    Current Password

                </label>

                <input

                    type="password"

                    className="form-control"

                    value={password}

                    onChange={e => setPassword(e.target.value)}

                    placeholder="Enter current password"

                />

            </div>

            <div>

                <label className="form-label">

                    New Password

                </label>

                <input

                    type="password"

                    className="form-control"

                    value={newPassword}

                    onChange={e => setNewPassword(e.target.value)}

                    placeholder="Enter new password"

                />

            </div>

        </Modal>

    );

}

export default ChangePasswordModal;