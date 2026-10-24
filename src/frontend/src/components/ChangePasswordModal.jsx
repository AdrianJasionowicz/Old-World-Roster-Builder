import { useState } from "react";

import Modal from "./Modal";
import { changePassword } from "../api/accountApi";
import { useSubmit } from "../hooks/useSubmit";

function ChangePasswordModal({ show, onClose }) {
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [repeatedPassword, setRepeatedPassword] = useState("");
    const { submit, saving, error, setError } = useSubmit();

    async function handleSave() {
        if (newPassword !== repeatedPassword) {
            setError("New passwords do not match.");
            return;
        }
        if (await submit(() => changePassword({ password, newPassword }))) {
            onClose();
        }
    }

    return (
        <Modal show={show} title="Change Password" onClose={onClose} onSave={handleSave} saving={saving} error={error}>
            <div className="mb-3">
                <label className="form-label">Current password</label>
                <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required autoFocus />
            </div>
            <div className="mb-3">
                <label className="form-label">New password</label>
                <input type="password" className="form-control" value={newPassword} onChange={e => setNewPassword(e.target.value)} required />
            </div>
            <div>
                <label className="form-label">Repeat new password</label>
                <input type="password" className="form-control" value={repeatedPassword} onChange={e => setRepeatedPassword(e.target.value)} required />
            </div>
        </Modal>
    );
}

export default ChangePasswordModal;
